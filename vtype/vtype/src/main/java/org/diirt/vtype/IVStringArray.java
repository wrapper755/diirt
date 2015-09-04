/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import java.util.List;

import org.diirt.util.array.ListInt;
import org.diirt.util.array.ListLong;

/**
 *
 * @author carcassi
 */
class IVStringArray extends VStringArray {
    
    private final List<String> data;
    private final ListInt sizes;
    private final Alarm alarm;
    private final Time time;

    IVStringArray(List<String> data, ListInt sizes, Alarm alarm, Time time) {
        this.data = data;
        this.alarm = alarm;
        this.time = time;
        this.sizes = sizes;
    }

    @Override
    public ListInt getSizes() {
        return sizes;
    }

    @Override
    public List<String> getData() {
        return data;
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
