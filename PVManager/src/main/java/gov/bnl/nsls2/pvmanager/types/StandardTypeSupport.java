/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.bnl.nsls2.pvmanager.types;

import gov.bnl.nsls2.pvmanager.NullUtils;
import gov.bnl.nsls2.pvmanager.TypeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Adds support for standard types.
 * <p>
 * Support for collections is at the level of raw types. Adding support for
 * generic types was not investigated but the feeling is that it would only
 * complicate the implementation. Given that the type checking is really
 * done when constructing the expression, the type checking is relegated
 * to the expression language part of the API.
 *
 * @author carcassi
 */
class StandardTypeSupport {

    private static boolean installed = false;

    static void install() {
        // Install only once
        if (installed)
            return;

        addDouble();
        addDoubleStatistics();
        addSynchronizedArray();
        addList();

        installed = true;
    }
    
    private static void addList() {
        TypeSupport.addTypeSupport(List.class, new TypeSupport<List>() {

            @Override
            @SuppressWarnings("unchecked")
            public Notification<List> prepareNotification(List oldValue, List newValue) {
                // Initialize value if never initialized
                if (oldValue == null)
                    oldValue = new ArrayList();

                boolean notificationNeeded = false;

                // Check all the elements in the list and use StandardTypeSupport
                // to understand whether any needs notification.
                // Notification is done only if at least one element needs notification.
                for (int index = 0; index < newValue.size(); index++) {
                    if (oldValue.size() <= index) {
                        oldValue.add(null);
                    }

                    if (newValue.get(index) != null) {
                        Notification itemNotification = TypeSupport.notification(oldValue.get(index), newValue.get(index));
                        if (itemNotification.isNotificationNeeded()) {
                            notificationNeeded = true;
                            oldValue.set(index, itemNotification.getNewValue());
                        }
                    }
                }

                return new Notification<List>(notificationNeeded, oldValue);
            }
        });
    }
    
    private static void addDouble() {
        // Add Double support: simply return the new value
        TypeSupport.addTypeSupport(Double.class, new TypeSupport<Double>() {
            @Override
            public Notification<Double> prepareNotification(Double oldValue, Double newValue) {
                if (NullUtils.equalsOrBothNull(oldValue, newValue))
                    return new Notification<Double>(false, null);
                return new Notification<Double>(true, newValue);
            }
        });
    }

    private static void addDoubleStatistics() {
        // Add DoubleStatistics support: copy the new values in the old object.
        TypeSupport.addTypeSupport(DoubleStatistics.class, new TypeSupport<DoubleStatistics>() {
            @Override
            public Notification<DoubleStatistics> prepareNotification(DoubleStatistics oldValue, DoubleStatistics newValue) {
                if (oldValue == null)
                    oldValue = new DoubleStatistics();
                if (oldValue.equals(newValue))
                    return new Notification<DoubleStatistics>(false, null);
                oldValue.setStatistics(newValue.getAverage(), newValue.getMin(),
                        newValue.getMax(), newValue.getStdDev());
                return new Notification<DoubleStatistics>(true, oldValue);
            }
        });
    }

    private static void addSynchronizedArray() {
        // Add DoubleStatistics support: copy the new values in the old object.
        TypeSupport.addTypeSupport(SynchronizedArray.class, new TypeSupport<SynchronizedArray>() {
            @Override
            public Notification<SynchronizedArray> prepareNotification(SynchronizedArray oldValue, SynchronizedArray newValue) {
                if (newValue == null)
                    return new Notification<SynchronizedArray>(false, null);
                if (oldValue == null)
                    oldValue = new SynchronizedArray();
                if (newValue.getTimeStamp().equals(oldValue.getTimeStamp()))
                    return new Notification<SynchronizedArray>(false, null);
                oldValue.setTimeStamp(newValue.getTimeStamp());
                oldValue.getValues().clear();
                oldValue.getValues().addAll(newValue.getValues());
                return new Notification<SynchronizedArray>(true, oldValue);
            }
        });
    }

}
