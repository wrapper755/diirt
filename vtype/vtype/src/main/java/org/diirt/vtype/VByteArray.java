/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import org.diirt.util.array.ListByte;

/**
 * Byte array with alarm, timestamp, display and control information.
 *
 * @author carcassi
 */
public abstract class VByteArray extends VNumberArray {
    
	   
    /**
     * {@inheritDoc }
     */
    @Override
    public abstract ListByte getData();
    
    /**
     * Creates a new VByteArray.
     * 
     * @param data the value
     * @param alarm the alarm
     * @param time the time
     * @param display the display
     * @return the new value
     */
    public static VByteArray create(final ListByte data, final Alarm alarm, final Time time, final Display display) {
        return new IVByteArray(data, null, alarm, time, display);
    }
}
