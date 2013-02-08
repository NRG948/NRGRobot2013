/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import edu.wpi.first.wpilibj.Servo;
/**
 *
 * @author Sean
 */
public class NRGCamera extends Subsystem{
    public static Servo servo = RobotMap.cameraServo;
    public static double servoAngle;
    final static double downAngle = 0d;
    final static double uprightAngle = 30d;
    protected void initDefaultCommand() {
    }
    
    public static double getDistance(){
        return 0;
    }
    
    public static void changeServoAngle(double desiredAngle){ //changes the angle of servo to given angle
       servo.setAngle(desiredAngle);
    }
    
    public static double getServoAngle(){
        servoAngle = servo.getAngle();
        return servoAngle; 
    }
    
}
