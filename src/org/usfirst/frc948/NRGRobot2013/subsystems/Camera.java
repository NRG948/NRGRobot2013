/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;
import edu.wpi.first.wpilibj.camera.AxisCameraException;

/**
 *
 * @author Sean
 */
public class Camera extends Subsystem{
    public static AxisCamera axisCamera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation   // create the criteria for the particle filter
    public static Servo servo = RobotMap.cameraServo;
    public static double servoAngle;
    final static double downAngle = 0d;
    final static double uprightAngle = 30d;
    final double highAspect=62.0/20.0;
    final double middleAspect=62.0/29.0;
    final double lowAspect=37.0/32.0;
    final int TOL =15;
    final double IMAGEWIDTH=320.0;
    final double TANGENT =Math.tan((43.5/180.0*Math.PI));
    protected void initDefaultCommand() {

    }
    
    public Camera(){
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
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
