package org.usfirst.frc948.NRGRobot2013.utilities;

/**
 *
 * @author Kevin
 */
public class Debug {

    private static boolean print = true;
    
    public static final int ROBOT_ROUTINES = -1;
    public static final int AUTONOMOUS = 0;
    public static final int IR = 1;
    public static final int DRIVE = 2;
    public static final int JOYSTICK = 3;
    public static final int GYRO = 4;
    public static final int MAE = 5;
    public static final int SHOOTER = 6;
    public static final int IO = 7;
    public static final int CLIMBER = 8;
    public static final int MAE_CALIBRATION = 9;
    public static final int TIMERS = 11;
    private static int toggleIndex = 0;
    private static boolean[] isEnabled = {true, true, true, true, true, true, true, true, true, true, true, true};

    public static void enable() {
        print = true;
    }

    public static void disable() {
        print = false;
    }

    public static void ToggleDebug() {
        isEnabled[toggleIndex] = !isEnabled[toggleIndex];
    }

    private static boolean isEnabled(int n) {
        if (n < 0) {
            return true;
        }

        return isEnabled[n];
    }

    /**
     * prints line
     *
     * @param debug flag
     * @param s string
     */
    public static void print(int debug, String s) {
        if (print && isEnabled(debug)) {
            System.out.print(s);
        }
    }
    public static void println(int debug, String s) {
        if (print && isEnabled(debug)) {
            System.out.println(s);
        }
    }
    public static void printException(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    /**
     *
     * @param debug the flag
     * @param output the array of output values
     * @param s the message WITH number placeholder
     * @param maxDecimalPlaces how many decimal places for the number input
     */
    public static void printRoundLn(int debug, String s, double[] output, int maxDecimalPlaces) {
        if (print && isEnabled(debug)) {
            String printString = "";
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '/') {
                    try {
                        printString += MathHelper.round(output[Integer.parseInt(s.substring(i + 1, i + 2))],
                                maxDecimalPlaces);
                    } catch (Exception e) {
                        printException(e);
                    }
                } else {
                    printString += c;
                }
            }
            System.out.println(printString);
        }
    }
}
