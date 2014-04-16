/**
 * Copyright (C) 2012-14 graphene developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.graphene;

import java.util.HashMap;

/**
 *
 * @author carcassi, sjdallst
 */
public class MultiYAxisGraph2DRendererUpdate extends Graph2DRendererUpdate<MultiYAxisGraph2DRendererUpdate> {

    private HashMap<Integer, Range> IndexToRangeMap = new HashMap<Integer, Range>();
    private Integer marginBetweenGraphs,
            minimumGraphHeight;
    
    private InterpolationScheme interpolation;
    private ReductionScheme reduction;
    
    private Integer minimumGraphWidth;
    
    public MultiYAxisGraph2DRendererUpdate minimumGraphWidth(int minimumGraphWidth){
        this.minimumGraphWidth = minimumGraphWidth;
        return self();
    }
    
    /**
     *Sets this object's interpolation to the given scheme.
     * @param scheme can not be null, must be a supported scheme. Supported schemes:NEAREST_NEIGHBOUR,LINEAR,CUBIC.
     * @return this
     */
    public MultiYAxisGraph2DRendererUpdate interpolation(InterpolationScheme scheme) {
        if (scheme == null) {
            throw new NullPointerException("Interpolation scheme can't be null");
        }
        if (!MultiYAxisGraph2DRenderer.supportedInterpolationScheme.contains(scheme)) {
            throw new IllegalArgumentException("Interpolation " + scheme + " is not supported");
        }
        this.interpolation = scheme;
        return this;
    }
    
    /**
     *Sets this object's data reduction scheme to the given scheme.
     * @param scheme can not be null, must be a supported scheme. Supported schemes:FIRST_MAX_MIN_LAST,NONE
     * @return this
     */
    public MultiYAxisGraph2DRendererUpdate dataReduction(ReductionScheme scheme) {
        if (scheme == null) {
            throw new NullPointerException("Data reduction scheme can't be null");
        }
        if (!MultiYAxisGraph2DRenderer.supportedReductionScheme.contains(scheme)) {
            throw new IllegalArgumentException("Data reduction " + scheme + " is not supported");
        }
        this.reduction = scheme;
        return this;
    }
    
    public MultiYAxisGraph2DRendererUpdate marginBetweenGraphs(Integer margin){
        marginBetweenGraphs = margin;
        return this.self();
    }
    
    public MultiYAxisGraph2DRendererUpdate minimumGraphHeight(Integer minimumGraphHeight){
        this.minimumGraphHeight = minimumGraphHeight;
        return this.self();
    }
    
    public MultiYAxisGraph2DRendererUpdate setRanges(HashMap<Integer, Range> map){
        IndexToRangeMap.putAll(map);
        return this.self();
    }
    
    public HashMap<Integer, Range> getIndexToRange(){
        return IndexToRangeMap;
    }
    
    public Integer getMarginBetweenGraphs(){
        return marginBetweenGraphs;
    }
    
    public Integer getMinimumGraphHeight(){
        return minimumGraphHeight;
    }
    
    /**
     *Current interpolation scheme
     * @return the current interpolation scheme.
     */
    public InterpolationScheme getInterpolation() {
        return interpolation;
    }

    /**
     *Current reduction scheme
     * @return the current reduction scheme.
     */
    public ReductionScheme getDataReduction() {
        return reduction;
    }
    
    public Integer getMinimumGraphWidth(){
        return minimumGraphWidth;
    }
}
