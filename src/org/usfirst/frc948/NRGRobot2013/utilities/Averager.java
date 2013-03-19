package org.usfirst.frc948.NRGRobot2013.utilities;

/**
 * ...now more convoluted than ever before!
 * 
 * @author irving
 */
public class Averager {

    private final int max;
    
    private int count;
    private int idx;
    private final double[] array;
    
    private double average;

    /**
     * @param n number of values to average over
     */
    public Averager(int n) {
        max = n;
        count = 0;
        idx = 0;
        array = new double[n];
    }

    public void add(double n) {
        if (count == max) {
            count--;
        }
        
        array[idx] = n;
        count++;
        idx++;
        average = sum() / count;
        
        if (idx == max) {
            idx = 0;
        }
    }
    
    private double sum() {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {sum += array[i];}
        return sum;
    }
    
    public double getAverage() {
        return average;
    }
}
