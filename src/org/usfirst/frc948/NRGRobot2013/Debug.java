/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013;

/**
 *
 * @author Patrick
 */
public class Debug {

    public static final int FATAL_EXCEPTIONS = -1;
    public static final int IO = 7;
    private static boolean print = false;

    public static void println(int debug, String message) {
        if (print) {
            System.out.println(message);
        }
    }
    
    public static void enable() {
        print = true;
    }
    
    public static void disable() {
        print = false;
    }
}
