/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import org.diirt.util.array.ListBoolean;
import org.diirt.util.array.ListInt;

/**
 *
 * @author carcassi
 */
class IVBooleanArray extends VBooleanArray {
    
    private final ListBoolean data;
    private final ListInt sizes;
    private final Alarm alarm;
    private final Time time;

    IVBooleanArray(ListBoolean data, ListInt sizes, Alarm alarm, Time time) {
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
    public ListBoolean getData() {
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
