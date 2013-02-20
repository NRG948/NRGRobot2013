package org.usfirst.frc948.NRGRobot2013;

import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc948.NRGRobot2013.utilities.Averager;

/**
 * Extends library Encoder class to do manual RPM calculation (e.g. on a wheel)
 *
 * @author irving
 */
public class NRGEncoder extends Encoder {
    
    private final int countsPerRevolution;
    
    private long lastTime;
    private int lastCount;
    
    private double RPM;
    
    private Averager rpmAverager;
    
    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * @param aSlot the a channel digital input module.
     * @param aChannel the a channel digital input channel.
     * @param bSlot the b channel digital input module.
     * @param bChannel the b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
     * selected, then an encoder FPGA object is used and the returned counts will be 4x the encoder
     * spec'd value since all rising and falling edges are counted. If 1X or 2X are selected then
     * a counter object will be used and the returned value will either exactly match the spec'd count
     * or be double (2x) the spec'd count.
     * @param countsPerRevolution measured counts per revolution (try test mode?)
     */
    public NRGEncoder(final int aSlot, final int aChannel, final int bSlot, final int bChannel, boolean reverseDirection, final EncodingType encodingType, int countsPerRevolution) {
        super(aSlot, aChannel, bSlot, bChannel, reverseDirection, encodingType);
        this.countsPerRevolution = countsPerRevolution;
        lastTime = 0;
        lastCount = 0;
        rpmAverager = new Averager(10);
    }
    
    /**
     * Recalculates RPM and average RPM; should be called periodically.
     */
    public void update() {
        long currTime = System.currentTimeMillis() + 1;
        
        while (currTime > System.currentTimeMillis()) {}
        
        int currCount = getRaw();
        
        double minutes = (currTime - lastTime) / 60000.0;
        double revolutions = (double) (currCount - lastCount) / countsPerRevolution;
        RPM = revolutions / minutes;
        
        lastTime = currTime;
        lastCount = currCount;
        
        rpmAverager.add(RPM);
    }
    
    /**
     * Returns last calculated instantaneous RPM.
     */
    public double getRPM() {
        return RPM;
    }
    
    /**
     * Returns average of last 10 RPM calculations.
     */
    public double averageRPM() {
        return rpmAverager.getAverage();
    }
}
