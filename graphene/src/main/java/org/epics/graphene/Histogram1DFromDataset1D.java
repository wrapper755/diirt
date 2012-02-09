/*
 * Copyright 2011 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.graphene;

/**
 *
 * @author carcassi
 */
class Histogram1DFromDataset1D implements Histogram1D {
    
    private int imageHeight;
    private int imageWidth;
    private double minValueRange;
    private double maxValueRange;
    private int minCountRange;
    private int maxCountRange;
    private double[] binValueBoundary;
    private int[] binCount;
    private boolean autoValueRange = true;
    private int nBins = 100;
    

    @Override
    public int getImageHeight() {
        return imageHeight;
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public double getMinValueRange() {
        return minValueRange;
    }

    @Override
    public double getMaxValueRange() {
        return maxValueRange;
    }

    public void setMaxValueRange(double maxValueRange) {
        this.maxValueRange = maxValueRange;
    }

    public void setMinValueRange(double minValueRange) {
        this.minValueRange = minValueRange;
    }
    
    @Override
    public void setImageHeight(int height) {
        this.imageHeight = height;
    }

    @Override
    public void setImageWidth(int width) {
        this.imageWidth = width;
    }

    @Override
    public int getMaxCountRange() {
        return maxCountRange;
    }

    public void setMaxCountRange(int maxCountRange) {
        this.maxCountRange = maxCountRange;
    }

    @Override
    public int getMinCountRange() {
        return minCountRange;
    }

    public void setMinCountRange(int minCountRange) {
        this.minCountRange = minCountRange;
    }

    @Override
    public int getNBins() {
        return binCount.length;
    }

    @Override
    public double getBinValueBoundary(int index) {
        return binValueBoundary[index];
    }

    @Override
    public int getBinCount(int index) {
        return binCount[index];
    }

    public void setBinCount(int[] binCount) {
        this.binCount = binCount;
    }

    public void setBinValueBoundary(double[] binValueBoundary) {
        this.binValueBoundary = binValueBoundary;
    }
    
    public void setDataset(Dataset1D dataset) {
        IteratorDouble values = dataset.getValues();
        if (autoValueRange) {
            this.minValueRange = dataset.getMinValue();
            this.maxValueRange = dataset.getMaxValue();
            binValueBoundary = RangeUtil.createBins(minValueRange, maxValueRange, nBins);
        }
        binCount = new int[nBins];
        while (values.hasNext()) {
            addValueToBin(values.next());
        }
    }
    
    private void addValueToBin(double value) {
        // Discard value outsie the binning area
        if (value < getBinValueBoundary(0) || value > getBinValueBoundary(nBins)) {
            return;
        }
        
        int bin = (int) Math.floor(NumberUtil.scale(value, getBinValueBoundary(0), getBinValueBoundary(nBins), nBins));
        if (bin == nBins) {
            bin--;
        }
        
        binCount[bin]++;
    }

    
}
