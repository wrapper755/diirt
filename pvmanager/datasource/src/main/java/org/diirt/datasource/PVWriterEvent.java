/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.datasource;

/**
 * An event for the writer.
 * <p>
 * An event can be trigger by:
 * <ul>
 * <li>a connection change; the connection either connected or disconnected</li>
 * <li>an exception; an error either at the datasource or while preparing the write values</li>
 * <li>a write has concluded</li>
 * </ul>
 * One event can have multiple triggers. You can use the mask or the methods to check what condition applies.
 *
 * @param <T> the type of the writer
 * @author carcassi
 */
public class PVWriterEvent<T> {

    /**
     * Mask for connection event.
     */
    public static int CONNECTION_MASK      = 0b000001;
    /**
     * Mask for error event.
     */
    public static int EXCEPTION_MASK       = 0b000010;
    /**
     * Mask for a successful write result.
     */
    public static int WRITE_SUCCEEDED_MASK = 0b000100;
    /**
     * Mask for a failed write result.
     */
    public static int WRITE_FAILED_MASK    = 0b001000;

    private final int notificationMask;
    private final PVWriter<T> pvWriter;

    PVWriterEvent(int notificationMask, PVWriter<T> pvWriter) {
        this.notificationMask = notificationMask;
        this.pvWriter = pvWriter;
    }

    /**
     * The writer that generated the event.
     *
     * @return the pv writer
     */
    public PVWriter<T> getPvWriter() {
        return pvWriter;
    }

    /**
     * The mask for the event.
     *
     * @return event mask
     */
    public int getNotificationMask() {
        return notificationMask;
    }

    /**
     * Whether this event was generated by a connection change.
     *
     * @return true if the connection status changed from the last notification
     */
    public boolean isConnectionChanged() {
        return (notificationMask & CONNECTION_MASK) != 0;
    }

    /**
     * Whether this event was generated in response to a successful write operation.
     *
     * @return true if the write operation was concluded successfully
     */
    public boolean isWriteSucceeded() {
        return (notificationMask & WRITE_SUCCEEDED_MASK) != 0;
    }

    /**
     * Whether this event waas generated in response to a failed write operation.
     *
     * @return true if the write operation was concluded unsuccessfully
     */
    public boolean isWriteFailed() {
        return (notificationMask & WRITE_FAILED_MASK) != 0;
    }

    /**
     * Whether this event was generated in response to an error.
     *
     * @return true if a new exception is available
     */
    public boolean isExceptionChanged() {
        return (notificationMask & EXCEPTION_MASK) != 0;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        boolean first = true;
        if (isConnectionChanged()) {
            builder.append("CONN");
            first = false;
        }
        if (isWriteFailed()) {
            if (!first) {
                builder.append(" | ");
            }
            builder.append("WRITE_FAIL");
            first = false;
        }
        if (isWriteSucceeded()) {
            if (!first) {
                builder.append(" | ");
            }
            builder.append("WRITE_SUCC");
            first = false;
        }
        if (isExceptionChanged()) {
            if (!first) {
                builder.append(" | ");
            }
            builder.append("EXC");
        }
        builder.append(" ]");
        return builder.toString();
    }

}
