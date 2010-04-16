/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MockPVFrame.java
 *
 * Created on Feb 16, 2010, 3:43:37 PM
 */

package gov.bnl.nsls2.pvmanager.test;

import gov.aps.jca.dbr.DBR_TIME_Double;
import gov.bnl.nsls2.pvmanager.MockConnectionManager;
import gov.bnl.nsls2.pvmanager.PV;
import gov.bnl.nsls2.pvmanager.PVManager;
import gov.bnl.nsls2.pvmanager.PVValueChangeListener;
import gov.bnl.nsls2.pvmanager.TimeDuration;
import gov.bnl.nsls2.pvmanager.types.DoubleStatistics;
import gov.bnl.nsls2.pvmanager.types.SynchronizedArray;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import static gov.bnl.nsls2.pvmanager.types.ExpressionLanguage.*;
import static gov.bnl.nsls2.pvmanager.jca.JCASupport.*;

/**
 *
 * @author carcassi
 */
public class MockSyncArrayFrame extends javax.swing.JFrame {

    /** Creates new form MockPVFrame */
    public MockSyncArrayFrame() {
        PVManager.setConnectionManager(MockConnectionManager.mockData());
        initComponents();
    }

    private JPanel oldPanel;

    private void updateChart() {
        if (oldPanel != null) {
            plotPanel.remove(oldPanel);
            oldPanel = null;
        }
        if (pv.getValue() == null)
            return;
        final JFreeChart chart = createCombinedChart();
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        plotPanel.add(panel);
        plotPanel.revalidate();
        oldPanel = panel;
    }

    private JFreeChart createCombinedChart() {


        // create subplot 1...
        final XYDataset data1 = createDataset1();
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis();
        rangeAxis1.setRange(-1.5, 1.5);
        final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);


//        // create subplot 2...
//        final XYDataset data2 = createDataset2();
//        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
//        final NumberAxis rangeAxis2 = new NumberAxis("Range 2");
//        rangeAxis2.setAutoRangeIncludesZero(false);
//        final XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
//        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        // parent plot...
        NumberAxis hor = new NumberAxis();
        hor.setRange(0, pv.getValue().getValues().size() - 1);
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(hor);
        plot.setGap(10.0);

        // add the subplots...
        plot.add(subplot1, 1);
//        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart(null,
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    private XYDataset createDataset1() {

        // create dataset 1...
        final XYSeries series1 = new XYSeries("Values at " + pv.getValue().getTimeStamp().asDate());
        int index = 0;
        if (pv.getValue() != null) {
            for (DBR_TIME_Double value : pv.getValue().getValues()) {
                if (value != null)
                    series1.add(index, value.getDoubleValue()[0]);
                index++;
            }
        }

//        final XYSeries series2 = new XYSeries("Series 2");
//        series2.add(10.0, 15000.3);
//        series2.add(20.0, 11000.4);
//        series2.add(30.0, 17000.3);
//        series2.add(40.0, 15000.3);
//        series2.add(50.0, 14000.4);
//        series2.add(60.0, 12000.3);
//        series2.add(70.0, 11000.5);
//        series2.add(80.0, 12000.3);
//        series2.add(90.0, 13000.4);
//        series2.add(100.0, 12000.6);
//        series2.add(110.0, 13000.3);
//        series2.add(120.0, 17000.2);
//        series2.add(130.0, 18000.2);
//        series2.add(140.0, 16000.2);
//        series2.add(150.0, 17000.2);

        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
//        collection.addSeries(series2);
        return collection;

    }

    /**
     * Creates a sample dataset.
     *
     * @return Series 2.
     */
    private XYDataset createDataset2() {

        // create dataset 2...
        final XYSeries series2 = new XYSeries("Series 3");

        series2.add(10.0, 16853.2);
        series2.add(20.0, 19642.3);
        series2.add(30.0, 18253.5);
        series2.add(40.0, 15352.3);
        series2.add(50.0, 13532.0);
        series2.add(100.0, 12635.3);
        series2.add(110.0, 13998.2);
        series2.add(120.0, 11943.2);
        series2.add(130.0, 16943.9);
        series2.add(140.0, 17843.2);
        series2.add(150.0, 16495.3);
        series2.add(160.0, 17943.6);
        series2.add(170.0, 18500.7);
        series2.add(180.0, 19595.9);

        return new XYSeriesCollection(series2);

    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        updateRateSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        samplesPerUpdateSpinner = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        nUpdatesSpinner = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        scanRateSpinner = new javax.swing.JSpinner();
        createPVButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nPVSpinner = new javax.swing.JSpinner();
        plotPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("PV parameters"));

        jLabel3.setText("PV update rate (Hz):");

        updateRateSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));

        jLabel4.setText("Samples per update:");

        samplesPerUpdateSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel5.setText("N updates:");

        nUpdatesSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateRateSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(samplesPerUpdateSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nUpdatesSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(updateRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(samplesPerUpdateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nUpdatesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("UI scan rate (Hz):");

        scanRateSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        createPVButton.setText("Create ");
        createPVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPVButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("N PVs:");

        nPVSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        plotPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(plotPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                    .addComponent(createPVButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scanRateSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nPVSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scanRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nPVSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createPVButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    PV<SynchronizedArray<DBR_TIME_Double>> pv;

    private void createPVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPVButtonActionPerformed
        pv = null;

        int nPvs = ((Integer) nPVSpinner.getModel().getValue()).intValue();
        long timeIntervalMs = (1000 / ((Integer) updateRateSpinner.getModel().getValue()).intValue());
        String pvName = "" + samplesPerUpdateSpinner.getModel().getValue() + "samples_every" + timeIntervalMs + "ms_for" + nUpdatesSpinner.getModel().getValue() + "times";
        int scanRate = ((Integer) scanRateSpinner.getModel().getValue()).intValue();

        pv = PVManager.read(synchronizedArrayOf(TimeDuration.ms(10), 
                epicsPvs(Collections.nCopies(nPvs, pvName), DBR_TIME_Double.class))).atHz(scanRate);
        pv.addPVValueChangeListener(new PVValueChangeListener() {
            @Override
            public void pvValueChanged() {
                updateChart();
            }
        });

    }//GEN-LAST:event_createPVButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MockSyncArrayFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createPVButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner nPVSpinner;
    private javax.swing.JSpinner nUpdatesSpinner;
    private javax.swing.JPanel plotPanel;
    private javax.swing.JSpinner samplesPerUpdateSpinner;
    private javax.swing.JSpinner scanRateSpinner;
    private javax.swing.JSpinner updateRateSpinner;
    // End of variables declaration//GEN-END:variables

}
