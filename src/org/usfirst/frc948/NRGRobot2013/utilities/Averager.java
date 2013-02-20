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
    private double sum;
    private double[] array;
    
    private double average;

    /**
     * @param n number of values to average over
     */
    public Averager(int n) {
        max = n;
        count = 0;
        idx = 0;
        sum = 0;
        array = new double[n];
    }

    public void add(double n) {
        if (count == max) {
            sum -= array[idx];
            count--;
        }
        
        array[idx] = n;
        sum += n;
        count++;
        idx++;
        average = sum / count;
        
        if (idx == max) {
            idx = 0;
        }
    }

    public double getAverage() {
        return average;
    }
}
