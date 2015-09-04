/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

import org.diirt.util.array.ListShort;

/**
 * Short array with alarm, timestamp, display and control information.
 *
 * @author carcassi
 */
public abstract class VShortArray extends VNumberArray {
    
	  /**
     * {@inheritDoc }
     */
    @Override
    public abstract ListShort getData();
    
    /**
     * Creates a new VShortArray.
     * 
     * @param data the value
     * @param alarm the alarm
     * @param time the time
     * @param display the display
     * @return the new value
     */
    public static VShortArray create(final ListShort data, final Alarm alarm, final Time time, final Display display) {
        return new IVShortArray(data, null, alarm, time, display);
    }
}
