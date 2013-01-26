// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Charles, Jared
 */
public class Drive extends PIDSubsystem {
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController leftMotor1 = RobotMap.driveleftMotor1;
    SpeedController leftMotor2 = RobotMap.driveleftMotor2;
    SpeedController rightMotor1 = RobotMap.driverightMotor1;
    SpeedController rightMotor2 = RobotMap.driverightMotor2;
    Encoder leftQuadrature = RobotMap.driveleftQuadrature;
    Encoder rightQuadrature = RobotMap.driverightQuadrature;
    Gyro gyro = RobotMap.drivegyro; 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private double pidOutput;
    private double desiredHeading;
    static final double kP = 0.02;
    static final double kI = 0.005;
    static final double kD = 0.001;
    
    public Drive(){
        super ("DrivePID", kP, kI, kD);
    }
    
    public void driveStraight(double speed, double heading){
        
        double leftSpeed = speed;
        double rightSpeed = speed - pidOutput;
        if(rightSpeed > 1d || rightSpeed <-1d){
            leftSpeed = speed + pidOutput;
            rightSpeed = speed;
        }
        tankDrive(leftSpeed, rightSpeed);
    }
    
    public void tankDrive(double leftPower, double rightPower) {
        leftMotor1.set(leftPower);
        leftMotor2.set(leftPower);
        rightMotor1.set(-rightPower);
        rightMotor2.set(-rightPower);
    }
    
    public void stop(){
        tankDrive(0,0);
    }
    
    public double getDesiredHeading() {
        return desiredHeading;
    }
    
    public void setDesiredHeading(double newHeading) {
        desiredHeading = newHeading;
    }
    
    public double getGyroAngle() {
        return gyro.getAngle();
    }
    public void resetGyro(){
        gyro.reset();
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new OperatorDriveCommand());
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        return getGyroAngle();
    }

    protected void usePIDOutput(double d) {
        pidOutput = d;
    }
}
