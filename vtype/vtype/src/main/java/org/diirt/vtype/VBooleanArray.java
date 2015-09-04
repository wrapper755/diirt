/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import org.diirt.util.array.ListBoolean;

/**
 * Byte array with alarm, timestamp, display and control information.
 *
 * @author carcassi
 */
public abstract class VBooleanArray extends Array implements AlarmProvider, TimeProvider {
    
    /**
     * {@inheritDoc }
     */
    @Override
    public abstract ListBoolean getData();
    
    /**
     * Creates a new VBooleanArray.
     * 
     * @param data the value
     * @param alarm the alarm
     * @param time the time
     * @param display the display
     * @return the new value
     */
    public static VBooleanArray create(final ListBoolean data, final Alarm alarm, final Time time) {
        return new IVBooleanArray(data, null, alarm, time);
    }
}
