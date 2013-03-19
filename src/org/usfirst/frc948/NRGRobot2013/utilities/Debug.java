package org.usfirst.frc948.NRGRobot2013.utilities;

/**
 *
 * @author Kevin
 */
public class Debug {

    private static boolean print = false;
    
    public static void enable() {
        print = true;
    }

    public static void disable() {
        print = false;
    }

    public static void printException(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    public static void print(String s) {
        if (print) {
            System.out.print(s);
        }
    }
    
    public static void println(String s) {
        if (print) {
            System.out.println(s);
        }
    }
}
