/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

/**
 * Simple implementation for VBoolean.
 * 
 * @author carcassi
 */
class IVBoolean extends VBoolean {
    
    private final Boolean value;
    private final Alarm alarm;
    private final Time time;

    IVBoolean(Boolean value, Alarm alarm, Time time) {
        this.value = value;
        this.alarm = alarm;
        this.time = time;
    }

    @Override
    public Boolean getValue() {
        return value;
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
