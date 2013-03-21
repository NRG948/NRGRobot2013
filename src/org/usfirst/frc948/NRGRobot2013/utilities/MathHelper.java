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
    
    private static final double m = 0.000266123957;
    private static final double b = -0.040707308941;
    public static double RpmToPower(double RPM) {
        if (RPM == 0) return 0;
//        return 0.0002470137 * RPM + 0.0014988179;
//        return 0.0002437357 * RPM + 0.1122354842;
          return m * RPM + b;
    }
    
    public static double PowerToRpm(double power) {
        if (power == 0) return 0;
        return (power - b) / m;
    }
    
    public static double HeadingToDegrees(double heading) {
        double degrees = 90 - heading;
        return degrees;
    }
    
    /**
     * Convert a heading into an equivalent heading which is within 180 degrees of the gyroHeading.
     * 
     * @param heading The heading of which we're trying to find an equivalent that is close to gyroHeading.
     * @param gyroHeading The current value of the gyro.
     * @return heading+n*360 where n is chosen such that the result is within 180 degrees of gyroHeading.
     */
    public static double nearestEquivalentHeading(double heading, double gyroHeading)
    {
        double deltaAngle = (heading - gyroHeading) % 360;
        if (deltaAngle > 180) {
            deltaAngle -= 360;
        } else if (deltaAngle <= -180) {
            deltaAngle += 360;
        }
        return gyroHeading + deltaAngle;
    }
}