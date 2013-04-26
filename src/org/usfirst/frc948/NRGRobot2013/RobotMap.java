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
    
import edu.wpi.first.wpilibj.CounterBase.EncodingType; 
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc948.NRGRobot2013.subsystems.Camera;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    public static SpeedController driveleftMotor1;
    public static SpeedController driveleftMotor2;
    public static SpeedController driverightMotor1;
    public static SpeedController driverightMotor2;
    
    public static Encoder driveleftQuadrature;
    public static Encoder driverightQuadrature;
    
    public static Gyro drivegyro;
    
    public static SpeedController shooterMotor;
    public static NRGEncoder shooterQuadrature;
    
//    public static Servo cameraServo;
    
    public static SpeedController climberMotor1;
    public static SpeedController climberMotor2;

    public static Relay magPiston;
    public static Relay tiltPiston;
    
    public static Compressor compressor;
    
    public static AnalogChannel IRSensor;
    
    public static AxisCamera camera;
    
    public static Servo extenderServoLeft;
    public static Servo extenderServoRight;
    
    public static final double DEFAULT_GYRO_SENSITIVITY = 0.00685;
    
    public static final boolean CompetitionRobot = true;   // ROBOT1: set true for Competition robot, false for practice bot
    
    public static void init() {
        
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        if (CompetitionRobot) {
            driveleftMotor1 = new Talon(1, 1);
            driveleftMotor2 = new Talon(1, 3);
            driverightMotor1 = new Talon(1, 2);
            driverightMotor2 = new Talon(1, 4);
            LiveWindow.addActuator("Drive", "leftMotor1", (Talon) driveleftMotor1);
            LiveWindow.addActuator("Drive", "leftMotor2", (Talon) driveleftMotor2);
            LiveWindow.addActuator("Drive", "rightMotor1", (Talon) driverightMotor1);
            LiveWindow.addActuator("Drive", "rightMotor2", (Talon) driverightMotor2);
        } else {
            driveleftMotor1 = new Jaguar(1, 1);
            driveleftMotor2 = new Jaguar(1, 3);
            driverightMotor1 = new Jaguar(1, 2);
            driverightMotor2 = new Jaguar(1, 4);
            LiveWindow.addActuator("Drive", "leftMotor1", (Jaguar) driveleftMotor1);
            LiveWindow.addActuator("Drive", "leftMotor2", (Jaguar) driveleftMotor2);
            LiveWindow.addActuator("Drive", "rightMotor1", (Jaguar) driverightMotor1);
            LiveWindow.addActuator("Drive", "rightMotor2", (Jaguar) driverightMotor2);
        }
 
        driveleftQuadrature = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
	LiveWindow.addSensor("Drive", "leftQuadrature", driveleftQuadrature);
        driveleftQuadrature.setDistancePerPulse(RobotMap.CompetitionRobot ? 0.002446363 : 0.00205071);
        driveleftQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveleftQuadrature.setReverseDirection(true);
        driveleftQuadrature.start();
        
        driverightQuadrature = new Encoder(1, 9, 1, 10, false, EncodingType.k4X);
	LiveWindow.addSensor("Drive", "rightQuadrature", driverightQuadrature);
        driverightQuadrature.setDistancePerPulse(RobotMap.CompetitionRobot ? 0.002446363 : 0.00247263);
        driverightQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        //driverightQuadrature.setReverseDirection(true);
        driverightQuadrature.start();
        
        drivegyro = new Gyro(1, 1);
	LiveWindow.addSensor("Drive", "gyro", drivegyro);
        drivegyro.setSensitivity(DEFAULT_GYRO_SENSITIVITY);
        drivegyro.reset();
        
        shooterMotor = new Jaguar(1, 8);
	LiveWindow.addActuator("Shooter", "shootMotor", (Jaguar) shooterMotor);
    
        shooterQuadrature = new NRGEncoder(1, 2, 1, 3, false, EncodingType.k1X, 400);
	LiveWindow.addSensor("Shooter", "shootQuadrature", shooterQuadrature);
        // 400 counts per revolution, dividing by this gives revolutions
        // rate calculated as distance per second, multiply by 60 for distance per minute (revolutions)
//        shooterQuadrature.setDistancePerPulse(60.0 / 400.0);
        shooterQuadrature.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterQuadrature.start();
        
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
//        climberMotor1 = new Jaguar(7);
//    LiveWindow.addActuator("Climber", "climberMotor1", (Jaguar) climberMotor1);
//    
//        climberMotor2 = new Jaguar(5);
//    LiveWindow.addActuator("Climber", "climberMotor2", (Jaguar) climberMotor2);
//    
//        tiltPiston = new Relay(3);
//        tiltPiston.setDirection(Relay.Direction.kReverse);
//    LiveWindow.addActuator("Climber", "tiltPiston", tiltPiston);
    
//        cameraServo = new Servo(9);
//        cameraServo.set(Camera.SERVO_SET_SHOOT);
//    LiveWindow.addActuator("Camera", "servo", cameraServo);
    
        compressor = new Compressor(6, 2);
    LiveWindow.addActuator("DiscMagazine", "Compressor", compressor);
        
        magPiston = new Relay(1);
    LiveWindow.addActuator("DiscMagazine", "magPiston", magPiston);
        
        IRSensor = new AnalogChannel(4);
    LiveWindow.addSensor("DiscMagazine", "IRSensor", IRSensor);
    
        camera = AxisCamera.getInstance();
        // TODO: REPLACE THESE WITH THE PROPER SETTINGS ONCE WE HAVE ACCESS TO
        //   M1013 CAMERA AGAIN
//        camera.writeExposureControl(AxisCamera.ExposureT.flickerfree60);
//        camera.writeWhiteBalance(AxisCamera.WhiteBalanceT.fixedOutdoor1);
        
        // TODO: FILL IN PROPER CHANNEL AFTER SERVOS ARE WIRED
        extenderServoLeft = new Servo(6);
        extenderServoRight = new Servo(9);
    }
}
