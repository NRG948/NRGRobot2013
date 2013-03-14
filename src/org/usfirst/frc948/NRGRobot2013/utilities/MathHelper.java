package org.usfirst.frc948.NRGRobot2013.utilities;

import com.sun.squawk.util.MathUtils;

/**
 *
 * @author hoileung
 */
public class MathHelper {

    /**
     * clamps val to [min, max]
     */
    public static double clamp(double val, double min, double max) {
        if (val > max) {
            return max;
        } else if (val < min) {
            return min;
        } else {
            return val;
        }
    }

    /**
     * clamps val to [min, max] then normalize it into a range of [0, 1]
     */
    public static double normalizeValue(double val, double min, double max) {
        return (clamp(val, min, max) - min) / (max - min);
    }

    /**
     * reverses the value of normalizeValue
     * 
     * @see MathHelper#normalizeValue(double, double, double) 
     */
    public static double reverseNormalizeValue(double val, double min, double max) {
        return 1 - normalizeValue(val, min, max);
    }

    /**
     * returns the angle between on range of [0, 360]
     */
    public static double normalizeAngle(double angle) {
        angle = angle % 360;
        
        if (angle < 0) {
            return angle + 360;
        } else {
            return angle;
        }
    }

    /**
     * raises the value of the first argument to the power of the second argument
     */
    public static double pow(double val, int power) {
        double num = 1;
        for (int i = 0; i < power; i++) {
            num *= val;
        }
        return num;
    }

    /**
     * rounds given number to specified number of digits after the decimal
     */
    public static double round(double val, int digits) {
        double x = pow(10, digits);
        return MathUtils.round(val * x) / x;
    }

    /**
     * returns the greater of two numbers
     */
    public static double max(double val1, double val2) {
        return val1 > val2 ? val1 : val2;
    }

    /**
     * returns the lesser of two numbers
     */
    public static double min(double val1, double val2) {
        return val1 < val2 ? val1 : val2;
    }
    
    public static double RpmToPower(double RPM) {
        if (RPM == 0) return 0;
        return 0.0002470137 * RPM + 0.0014988179;
    }
    
    public static double PowerToRpm(double power) {
        if (power == 0) return 0;
        return 4048.358451 * power - 6.067752;
    }
    
    public static double HeadingToDegrees(double heading) {
        double degrees = 90 - heading;
        return degrees;
    }
}