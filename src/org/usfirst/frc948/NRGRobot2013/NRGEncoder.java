package org.usfirst.frc948.NRGRobot2013;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;

/**
 *
 * @author irving
 */
public class NRGEncoder extends Encoder implements PIDSource {
    
    private final int countsPerRevolution;
    
    private long lastTime;
    private int lastCount;
    
    private double RPM;
    
    private Averager rpmAverager;
    
    public NRGEncoder(final int aSlot, final int aChannel, final int bSlot, final int bChannel, boolean reverseDirection, final EncodingType encodingType, int countsPerRevolution) {
        super(aSlot, aChannel, bSlot, bChannel, reverseDirection, encodingType);
        this.countsPerRevolution = countsPerRevolution;
        lastTime = 0;
        lastCount = 0;
        rpmAverager = new Averager(10);
    }
    
    public void update() {
        long currTime = System.currentTimeMillis() + 1;
        
        while (currTime > System.currentTimeMillis()) {;}
        
        int currCount = getRaw();
        
        SmartDashboard.putNumber("update period", currTime - lastTime);
        
        double minutes = (currTime - lastTime) / 60000.0;
        double revolutions = (double) (currCount - lastCount) / countsPerRevolution;
        RPM = revolutions / minutes;
        
        lastTime = currTime;
        lastCount = currCount;
        
        rpmAverager.add(RPM);
    }
    
    public double getRPM() {
        return RPM;
    }
    
    public double averageRPM() {
        return rpmAverager.getAverage();
    }
    
    public double pidGet() {
        return getRPM();
    }
    
    private class Averager {
        
        private int n;
        private Vector queue;
        
        // n - number of data points to average
        public Averager(int n) {
            this.n = n;
            queue = new Vector();
        }
        
        public void add(double n) {
            queue.addElement(new Double(n));
            if (queue.size() >= this.n) {
                queue.removeElementAt(0);
            }
        }
        
        private double sum() {
            double s = 0;
            for (int i = 0; i < queue.size(); i++) {
                s += ((Double) queue.elementAt(i)).doubleValue();
            }
            return s;
        }
        
        public double getAverage() {
            return (double) sum() / n;
        }
    }
}
