/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import java.util.ArrayList;
import java.util.List;

import org.diirt.util.array.ListInt;
import org.diirt.util.array.ListLong;

/**
 *
 * @author carcassi
 */
class IVEnumArray extends VEnumArray {
    
    private final ListInt indexes;
    private final List<String> labels;
    private final ListInt sizes;
    private final List<String> array;
    private final Alarm alarm;
    private final Time time;

    public IVEnumArray(ListInt indexes, List<String> labels, ListInt sizes, Alarm alarm, Time time) {
        List<String> tempArray = new ArrayList<>(indexes.size());
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.getInt(i);
            if (index < 0 || index >= labels.size()) {
                throw new IndexOutOfBoundsException("VEnumArray indexes must be within the label range");
            }
            tempArray.add(labels.get(index));
        }
        this.array = tempArray;
        this.indexes = indexes;
        this.labels = labels;
        this.sizes = sizes;
        this.alarm = alarm;
        this.time = time;               
    }

    @Override
    public List<String> getLabels() {
        return labels;
    }

    @Override
    public List<String> getData() {
        return array;
    }

    @Override
    public ListInt getIndexes() {
        return indexes;
    }

    @Override
    public ListInt getSizes() {
        return sizes;
    }

    @Override
    public String toString() {
        return VTypeToString.toString(this);
    }

    @Override
    public Alarm getAlarm() {
        return alarm;
    }

    @Override
    public Time getTime() {
        return time;
    }
    
}
