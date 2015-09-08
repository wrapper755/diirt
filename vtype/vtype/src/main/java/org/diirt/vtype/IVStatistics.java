/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.vtype;

/**
 * VStatistics implementation.
 *
 * @author carcassi
 */
class IVStatistics extends VStatistics {

    private final Double average;
    private final Double stdDev;
    private final Double min;
    private final Double max;
    private final Integer nSamples;
    private final Alarm alarm;
    private final Time time;
    private final Display display;
    

    public IVStatistics(Double average, Double stdDev, Double min, Double max, Integer nSamples,
            Alarm alarm, Time time, Display display) {
        this.average = average;
        this.stdDev = stdDev;
        this.min = min;
        this.max = max;
        this.nSamples = nSamples;
        this.alarm = alarm;
        this.time = time;
        this.display = display;
    }



    @Override
    public Double getAverage() {
        return average;
    }

    @Override
    public Double getStdDev() {
        return stdDev;
    }

    @Override
    public Double getMin() {
        return min;
    }

    @Override
    public Double getMax() {
        return max;
    }

    @Override
    public Integer getNSamples() {
        return nSamples;
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
