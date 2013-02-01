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
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
    public static SpeedController shootershootMotor;
    public static Encoder shootershootQuadrature;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController shooterAngleMotor; //added for aimsystem
    public static Encoder shooterAngleQuadrature; 
    public static SpeedController climberLeftMotor; //added for climber
    public static SpeedController climberRightMotor;
    
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
        drivegyro.setSensitivity(1.25);
        shootershootMotor = new Jaguar(1, 5);
	LiveWindow.addActuator("Shooter", "shootMotor", (Jaguar) shootershootMotor);
        shootershootQuadrature = new Encoder(1, 5, 1, 6, false, EncodingType.k4X);
	LiveWindow.addSensor("Shooter", "shootQuadrature", shootershootQuadrature);
        shootershootQuadrature.setDistancePerPulse(1.0);
        shootershootQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        shootershootQuadrature.start();
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        // value pulled from WPIlib: Gyro.kDefaultVoltsPerDegreePerSecond
        drivegyro.setSensitivity(DEFAULT_GYRO_SENSITIVITY);
        drivegyro.reset();
    }
}
