package org.usfirst.frc948.NRGRobot2013.utilities;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author hoileung
 */
public class LCD {

    public static boolean GYRO = true;
    public static boolean DRIVE = true;
    public static boolean AUTONOMOUS = true;
    public static boolean SHOOTER = true;
    public static boolean CAMERA = true;
    
    public static final int UPDATE_TIME = 5;
    public static int updateCounter = 0;
    
    private static final String emptyLine = emptyLine();
    private static String emptyLine() {
        String emptyLine = "";
        for (byte i = 0; i < DriverStationLCD.kLineLength; i++) {
            emptyLine += " ";
        }
        return emptyLine;
    }
    
    private final static DriverStationLCD lcd = DriverStationLCD.getInstance();
    
    //print method for the Driver Station
    public static void println(boolean flag, int line, String message) {
        if (flag) {
            switch (line) {
                case 1:
                    lcd.println(DriverStationLCD.Line.kUser1, 1, message);
                    break;
                case 2:
                    lcd.println(DriverStationLCD.Line.kUser2, 1, message);
                    break;
                case 3:
                    lcd.println(DriverStationLCD.Line.kUser3, 1, message);
                    break;
                case 4:
                    lcd.println(DriverStationLCD.Line.kUser4, 1, message);
                    break;
                case 5:
                    lcd.println(DriverStationLCD.Line.kUser5, 1, message);
                    break;
                case 6:
                    lcd.println(DriverStationLCD.Line.kUser6, 1, message);
                    break;
            }
        }
    }
    
    public static void clearLine(int line) {
        println(true, line, emptyLine);
    }
    
    // clears screen
    public static void clear() {
        for (byte i = 1; i <= 6; i++) {
            clearLine(i);
        }
    }
    
    // update LCD
    public static void update() {
        lcd.updateLCD();
    }
    
}
