/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import javax.sql.DataSource;
import org.diirt.service.ServiceMethod;
import org.diirt.util.array.CircularBufferDouble;
import org.diirt.util.time.Timestamp;
import org.diirt.vtype.VNumber;
import org.diirt.vtype.VString;
import org.diirt.vtype.VTable;
import org.diirt.vtype.ValueFactory;

/**
 * The implementation of a JDBC service method.
 *
 * @author carcassi
 */
class JDBCServiceMethod extends ServiceMethod {
    
    private final DataSource dataSource;
    private final String query;
    private final List<String> parameterNames;

    /**
     * Creates a new service method.
     * 
     * @param serviceMethodDescription a method description
     */
    JDBCServiceMethod(JDBCServiceMethodDescription serviceMethodDescription, JDBCServiceDescription serviceDescription) {
        super(serviceMethodDescription, serviceDescription);
        this.dataSource = serviceDescription.dataSource;
        this.query = serviceMethodDescription.query;
        this.parameterNames = serviceMethodDescription.orderedParameterNames;
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    protected String getQuery() {
        return query;
    }
    
    private boolean isResultQuery() {
        return !getResults().isEmpty();
    }

    protected List<String> getParameterNames() {
        return parameterNames;
    }

    @Override
    public Map<String, Object> syncExecImpl(Map<String, Object> parameters) throws Exception {
        try (Connection connection = getDataSource().getConnection())  {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getQuery())) {
                int i = 0;
                for (String parameterName : getParameterNames()) {
                    Object value = parameters.get(parameterName);
                    if (value instanceof VString) {
                        preparedStatement.setString(i+1, ((VString) value).getValue());
                    } else if (value instanceof VNumber) {
                        preparedStatement.setDouble(i+1, ((VNumber) value).getValue().doubleValue());
                    } else {
                        throw new RuntimeException("JDBC mapping support for " + value.getClass().getSimpleName() + " not implemented");
                    }
                    i++;
                }
                if (isResultQuery()) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    VTable table = resultSetToVTable(resultSet);
                    return Collections.<String, Object>singletonMap(getResults().get(0).getName(), table);
                } else {
                    preparedStatement.execute();
                    return new HashMap<String, Object>();
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Maps a result set to a VTable
     */
    static VTable resultSetToVTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int nColumns = metaData.getColumnCount();
        List<Class<?>> types = new ArrayList<>(nColumns);
        List<Object> data = new ArrayList<>(nColumns);
        List<String> names = new ArrayList<>(nColumns);
        for (int j = 1; j <= nColumns; j++) {
            names.add(metaData.getColumnName(j));
            switch (metaData.getColumnType(j)) {
                case Types.DOUBLE:
                case Types.FLOAT:
                    // XXX: NUMERIC should be BigInteger
                case Types.NUMERIC:
                    // XXX: Integers should be Long/Int
                case Types.INTEGER:
                case Types.TINYINT:
                case Types.BIGINT:
                case Types.SMALLINT:
                    types.add(double.class);
                    data.add(new CircularBufferDouble(Integer.MAX_VALUE));
                    break;
                    
                case Types.LONGNVARCHAR:
                case Types.CHAR:
                case Types.VARCHAR:
                    // XXX: should be a booloean
                case Types.BOOLEAN:
                case Types.BIT:
                    types.add(String.class);
                    data.add(new ArrayList<String>());
                    break;
                    
                case Types.TIMESTAMP:
                    types.add(Timestamp.class);
                    data.add(new ArrayList<Timestamp>());
                    break;
                    
                default:
                    if ("java.lang.String".equals(metaData.getColumnClassName(j))) {
                        types.add(String.class);
                        data.add(new ArrayList<String>());
                    } else {
                        throw new IllegalArgumentException("Unsupported type " + metaData.getColumnTypeName(j));
                    }

            }
        }
        
        while (resultSet.next()) {
            for (int i = 0; i < nColumns; i++) {
                Class<?> type = types.get(i);
                if (type.equals(String.class)) {
                    @SuppressWarnings("unchecked")
                    List<String> strings = (List<String>) data.get(i);
                    strings.add(resultSet.getString(i+1));
                } else if (type.equals(Timestamp.class)) {
                    @SuppressWarnings("unchecked")
                    List<Timestamp> timestamps = (List<Timestamp>) data.get(i);
                    java.sql.Timestamp sqlTimestamp = resultSet.getTimestamp(i+1);
                    if (sqlTimestamp == null) {
                        timestamps.add(null);
                    } else {
                        timestamps.add(Timestamp.of(new Date(sqlTimestamp.getTime())));
                    }
                } else if (type.equals(double.class)) {
                    ((CircularBufferDouble) data.get(i)).addDouble(resultSet.getDouble(i+1));
                }
            }
        }
        
        return ValueFactory.newVTable(types, names, data);
    }
}
