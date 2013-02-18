package org.usfirst.frc948.NRGRobot2013;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author irving
 */
public class NRGEncoder {
    
    private final Encoder encoder;
    private final int countsPerRevolution;
    
    private long lastTime;
    private int lastCount;
    
    private double RPM;
    
    public NRGEncoder(Encoder encoder, int countsPerRevolution) {
        this.encoder = encoder;
        this.countsPerRevolution = countsPerRevolution;
        lastTime = 0;
        lastCount = 0;
    }
    
    public void update() {
        long currTime = System.currentTimeMillis();
        int currCount = encoder.getRaw();
        
        SmartDashboard.putNumber("update period", currTime - lastTime);
        
        double minutes = (currTime - lastTime) / 60000.0;
        RPM = (double) (currCount - lastCount) / minutes / countsPerRevolution;
        
        lastTime = currTime;
        lastCount = currCount;
    }
    
    public double getRPM() {
        return RPM;
    }
}
