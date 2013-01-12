/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013;
import com.sun.squawk.util.MathUtils;
/**
 *
 * @author hoileung
 */
public class MathHelper {
    /**
     * Returns val if it is between max and min
     * if it is bigger than max, returns max
     * if it is smaller than min, returns min
     * @param val
     * @param max
     * @param min
     * @return 
     */
    public static double clamp(double val,double max,double min)
    {
        if(val>max)
        {
            return max;
        }else if(val<min)
        {
            return min;
        }else
        {
            return val;    
        }
    }
    public static int clamp(int val,int max, int min)
    {
        if(val>max)
        {
            return max;
        }
        else if(val<min)
        {
            return min;
        }
        else
        {
            return val;
        }
    }
    /**
     * returns the average of two numbers
     * @param val1
     * @param val2
     * @return 
     */
    public static double average(double val1,double val2)
    {
        return (val1+val2)/2;
    }
    /**
     * returns the average
     * @param val
     * @return 
     */
    public static double average(double[] val)
    {
        double total=0;
        for(int i = 0; i<val.length;i++)
        {
            total +=val[i];
        }
        return total/val.length;
    }
    /**
     * Returns the number to the x power
     * @param val
     * @param power
     * @return 
     */
    public static double pow(double val, int power)
    {
        double num = 1;
        for(int i =0; i<power;i++)
        {
            num*=val;
        }
        return num;
    }
    /**
     * Rounds the number to x number of digits
     * @param val
     * @param digits
     * @return 
     */
    public static double round(double val, int digits)
    {
       double x = pow(10,digits);
        return MathUtils.round(val*x)/x;
    }
    /**
     * Returns the max of two numbers
     * @param val1
     * @param val2
     * @return 
     */
    public static double max(double val1,double val2)
    {
        return val1>val2 ? val1: val2;
    }
    /**
     * Returns the min of two numbers
     * @param val1
     * @param val2
     * @return 
     */
    public static double min(double val1,double val2)
    {
        return val1<val2 ? val1: val2;
    }
}