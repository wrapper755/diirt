/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

/**
 * Scalar boolean with alarm and timestamp.
 *
 * @author carcassi
 */
public abstract class VBoolean extends Scalar {
    
    /**
     * {@inheritDoc }
     */
    @Override
    public abstract Boolean getValue();

    /**
     * Creates a new VBoolean.
     * 
     * @param value the value
     * @param alarm the alarm
     * @param time the time
     * @return the new value
     */
    public static VBoolean create(final Boolean value, final Alarm alarm, final Time time) {
        return new IVBoolean(value, alarm, time);
    }
}
