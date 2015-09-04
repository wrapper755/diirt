/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import org.diirt.util.array.ListFloat;
import org.diirt.util.array.ListInt;

/**
 *
 * @author carcassi
 */
class IVFloatArray extends VFloatArray {


    private final ListFloat data;
    private final ListInt sizes;
    private final Alarm alarm;
    private final Time time;
    private final Display display;

    IVFloatArray(ListFloat data, ListInt sizes, Alarm alarm, Time time, Display display) {
        this.data = data;
        this.alarm = alarm;
        this.time = time;
        this.display = display;
        this.sizes = sizes;
    }

    @Override
    public ListInt getSizes() {
        return sizes;
    }

    @Override
    public ListFloat getData() {
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

    @Override
    public Display getDisplay() {
        return display;
    }

}
