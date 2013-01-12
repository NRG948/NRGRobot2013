/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013;


import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author hoileung
 */
public class LCD {
    public static final boolean DRIVE = false;
    public static final boolean AUTONOMOUS = false;
    public static final boolean JOYSTICK = false; 
    public static final boolean PID = false;
    private final static DriverStationLCD lcd = DriverStationLCD.getInstance();
    
    //print method for the Driver Station
    public static void println(boolean flag, int line, String message)
    {
        if(flag)
        {
            switch(line)
            {
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
    //Clears screen
    public static void clear()
    {
        String clear = "";
        for(byte i = 0; i< DriverStationLCD.kLineLength;i++)
        {
            clear += "  ";
        }
        for(byte i = 1; i<=6; i++)
        {
            println(true,i,clear);
        }
    }
    //update LCD
    public static void update()
    {
        clear();
        updateText();
        lcd.updateLCD();
    }
    //update test on LCD
    public static void updateText()
    {
        
    }
}
