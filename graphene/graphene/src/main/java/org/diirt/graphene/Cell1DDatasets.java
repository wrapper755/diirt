/**
 * Copyright (C) 2010-14 diirt developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.diirt.graphene;

import org.diirt.util.stats.StatisticsUtil;
import org.diirt.util.stats.Statistics;
import org.diirt.util.stats.Range;
import org.diirt.util.array.ListNumber;
import org.diirt.util.array.ListNumbers;
import org.diirt.util.stats.Ranges;

/**
 * Factory methods for wrapper datasets.
 *
 * @author carcassi
 */
public class Cell1DDatasets {

    /**
     * Wraps a {@link ListNumber} into a {@link Point1DDataset}.
     * <p>
     * It assumes the argument is either immutable or mutable but
     * will not be changed in the future.
     *
     * @param values the values for the dataset
     * @param minValue the minimum value
     * @param maxValue the maximum value
     * @return the dataset from the values; never null
     */
    public static Cell1DDataset linearRange(final ListNumber values, final double minValue, final double maxValue) {
        final Statistics statistics = StatisticsUtil.statisticsOf(values);
        final Range range = Ranges.range(minValue, maxValue);
        final ListNumber xBoundaries = ListNumbers.linearListFromRange(minValue, maxValue, values.size() + 1);
        return new Cell1DDataset() {

            @Override
            public Range getDisplayRange() {
                return null;
            }

            @Override
            public double getValue(int x) {
                return values.getDouble(x);
            }

            @Override
            public Statistics getStatistics() {
                return statistics;
            }

            @Override
            public ListNumber getXBoundaries() {
                return xBoundaries;
            }

            @Override
            public Range getXRange() {
                return range;
            }

            @Override
            public int getXCount() {
                return values.size();
            }
        };
    }

    /**
     * Wraps {@link ListNumber}s for values and boundaries into a {@link Point1DDataset}.
     * <p>
     * It assumes the argument is either immutable or mutable but
     * will not be changed in the future.
     *
     * @param values the values for the dataset
     * @param xBoundaries the cell boundaries
     * @return the dataset from the values; never null
     */
    public static Cell1DDataset datasetFrom(final ListNumber values, final ListNumber xBoundaries) {
        final Statistics statistics = StatisticsUtil.statisticsOf(values);
        final Range range = Ranges.range(xBoundaries.getDouble(0), xBoundaries.getDouble(xBoundaries.size() - 1));
        return new Cell1DDataset() {

            @Override
            public Range getDisplayRange() {
                return null;
            }

            @Override
            public double getValue(int x) {
                return values.getDouble(x);
            }

            @Override
            public Statistics getStatistics() {
                return statistics;
            }

            @Override
            public ListNumber getXBoundaries() {
                return xBoundaries;
            }

            @Override
            public Range getXRange() {
                return range;
            }

            @Override
            public int getXCount() {
                return values.size();
            }
        };
    }

    public static Cell1DDataset datasetFrom(final ListNumber values, final ListNumber xBoundaries, final Range displayRange) {
        final Statistics statistics = StatisticsUtil.statisticsOf(values);
        final Range range = Ranges.range(xBoundaries.getDouble(0), xBoundaries.getDouble(xBoundaries.size() - 1));
        return new Cell1DDataset() {

            @Override
            public Range getDisplayRange() {
                return displayRange;
            }

            @Override
            public double getValue(int x) {
                return values.getDouble(x);
            }

            @Override
            public Statistics getStatistics() {
                return statistics;
            }

            @Override
            public ListNumber getXBoundaries() {
                return xBoundaries;
            }

            @Override
            public Range getXRange() {
                return range;
            }

            @Override
            public int getXCount() {
                return values.size();
            }
        };
    }

    public static Cell1DDataset createHistogram(Point1DDataset dataset) {
        Cell1DHistogramDataset histogram = new Cell1DHistogramDataset(dataset);
        return histogram;
    }
}
