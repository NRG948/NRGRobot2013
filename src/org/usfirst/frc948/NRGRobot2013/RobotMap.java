// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.

package org.usfirst.frc948.NRGRobot2013;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; 
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Servo;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveleftMotor1;
    public static SpeedController driveleftMotor2;
    public static SpeedController driverightMotor1;
    public static SpeedController driverightMotor2;
    public static Encoder driveleftQuadrature;
    public static Encoder driverightQuadrature;
    public static Gyro drivegyro;
    public static SpeedController shooterMotor;
    public static Encoder shooterQuadrature;
    public static Servo cameraServo;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static SpeedController shooterAngleMotor; //added for aimsystem
    
    public static Encoder azimuthQuadrature;
    
    public static SpeedController climberLeftMotor; //added for climber
    public static SpeedController climberRightMotor;

    public static Relay magPiston; //added for DiscMagazine

    public static DigitalInput minAngleSwitch; //switches that detect when shooter reaches max angle
    public static DigitalInput maxAngleSwitch;

    public static final double DEFAULT_GYRO_SENSITIVITY = 0.00685;
    
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveleftMotor1 = new Talon(1, 1);
	LiveWindow.addActuator("Drive", "leftMotor1", (Talon) driveleftMotor1);
        
        driveleftMotor2 = new Talon(1, 3);
	LiveWindow.addActuator("Drive", "leftMotor2", (Talon) driveleftMotor2);
        
        driverightMotor1 = new Talon(1, 2);
	LiveWindow.addActuator("Drive", "rightMotor1", (Talon) driverightMotor1);
        
        driverightMotor2 = new Talon(1, 4);
	LiveWindow.addActuator("Drive", "rightMotor2", (Talon) driverightMotor2);
        
        driveleftQuadrature = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
	LiveWindow.addSensor("Drive", "leftQuadrature", driveleftQuadrature);
        driveleftQuadrature.setDistancePerPulse(0.002446363);
        driveleftQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveleftQuadrature.setReverseDirection(true);
        driveleftQuadrature.start();
        driverightQuadrature = new Encoder(1, 9, 1, 10, false, EncodingType.k4X);
	LiveWindow.addSensor("Drive", "rightQuadrature", driverightQuadrature);
        driverightQuadrature.setDistancePerPulse(0.002446363);
        driverightQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        //driverightQuadrature.setReverseDirection(true);
        driverightQuadrature.start();
        
        drivegyro = new Gyro(1, 1);
	LiveWindow.addSensor("Drive", "gyro", drivegyro);
        drivegyro.setSensitivity(DEFAULT_GYRO_SENSITIVITY);
        drivegyro.reset();
        
        shooterMotor = new Jaguar(1, 5);
	LiveWindow.addActuator("Shooter", "shootMotor", (Jaguar) shooterMotor);
        shooterQuadrature = new Encoder(1, 4, 1, 14, false, EncodingType.k4X);
	LiveWindow.addSensor("Shooter", "shootQuadrature", shooterQuadrature);
        shooterQuadrature.setDistancePerPulse(1.0);//todo: need to calibrate the ratio
        shooterQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterQuadrature.start();
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        //TODO: Fix motor channels and slots, motor types
        shooterAngleMotor = new Jaguar(1, 6);
    LiveWindow.addActuator("Shooter", "shootAngleMotor", (Jaguar) shooterAngleMotor);
    
        azimuthQuadrature = new Encoder(11, 12);
    LiveWindow.addSensor("Shooter", "shooterAnglePotentiometer", azimuthQuadrature);
        
//        climberLeftMotor = new Jaguar(0, 0);
//    LiveWindow.addActuator("Climber", "climberLeftMotor", (Jaguar) climberLeftMotor);
//    
//        climberRightMotor = new Jaguar(0, 0);
//    LiveWindow.addActuator("Climber", "climberRightMotor", (Jaguar) climberRightMotor);
        
        minAngleSwitch = new DigitalInput(5);
    LiveWindow.addSensor("Aimsystem", "minAngleSwitch", minAngleSwitch);
    
        maxAngleSwitch = new DigitalInput(6);
    LiveWindow.addSensor("Aimsystem", "maxAngleSwitch", maxAngleSwitch);
        
//        magPiston = new Solenoid(0, 1);
//    LiveWindow.addActuator("DiscMagazine", "magPiston", magPiston);
        
//        cameraServo = new Servo(3, 0);
//    LiveWindow.addActuator("NRGCamera", "servo", cameraServo);
    }
}
