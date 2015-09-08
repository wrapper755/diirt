/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import java.util.List;

import org.diirt.util.array.ListInt;

/**
 *
 * @author carcassi
 */
public abstract class VEnumArray extends Array implements Enum, AlarmProvider, TimeProvider {
    
    @Override
    public abstract List<String> getData();

    public abstract ListInt getIndexes();
}
