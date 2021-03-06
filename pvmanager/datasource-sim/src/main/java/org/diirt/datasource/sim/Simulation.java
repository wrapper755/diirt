/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.datasource.sim;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

import org.diirt.util.time.TimeInterval;

/**
 * Base class for all simulated signals. It provides the common mechanism for
 * registering the update on a timer and a few other utilities.
 *
 * @author carcassi
 */
abstract class Simulation<T> {

    private static final Logger log = Logger.getLogger(Simulation.class.getName());

    private final long intervalBetweenExecution;
    private final Class<T> classToken;
    volatile Instant lastTime;

    /**
     * Creates a new simulation.
     *
     * @param secondsBeetwenSamples seconds between each samples
     */
    Simulation(Duration scanRate, Class<T> classToken) {
        // XXX: this is dead code at this point. If needed, use it when setting up
        // the scan job. Ignore otherwise.
        if (scanRate.compareTo(Duration.ofMillis(1)) < 0) {
            throw new IllegalArgumentException("Scans must be at least every ms (was " + scanRate + ")");
        }
        this.intervalBetweenExecution = Math.max(scanRate.toNanos() / 1000000, 1);
        this.classToken = classToken;
    }

    /**
     * Computes all the new values in the given time slice.
     *
     * @param interval the interval where the data should be generated
     * @return the new values
     */
    abstract List<T> createValues(TimeInterval interval);

    /**
     * Changes the time at which the data will be generated.
     *
     * @param lastTime new timestamp
     */
    void setLastTime(Instant lastTime) {
        this.lastTime = lastTime;
    }

}
