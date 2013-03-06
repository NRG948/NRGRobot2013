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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * @author Charles, Jared
 */
public class Drive extends PIDSubsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController leftMotor1 = RobotMap.driveleftMotor1;
    SpeedController leftMotor2 = RobotMap.driveleftMotor2;
    SpeedController rightMotor1 = RobotMap.driverightMotor1;
    SpeedController rightMotor2 = RobotMap.driverightMotor2;
    static Encoder leftQuadrature = RobotMap.driveleftQuadrature;
    static Encoder rightQuadrature = RobotMap.driverightQuadrature;
    Gyro gyro = RobotMap.drivegyro;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    private double pidOutput;
    private double desiredHeading;
    static final double kP = 0.01;
    static final double kI = 0.0025;
    static final double kD = 0.0005;
    private boolean RATE_LIMITING_ENABLED = true;
    private long lastTime;
    private double lastLeftPower = 0.0;
    private double lastRightPower = 0.0;
    
    // power change per millisecond
    public static final double DEFAULT_MAX_CHANGE = 0.0015;

    public Drive() {
        super("DrivePID", kP, kI, kD);
        this.getPIDController().setOutputRange(-0.1, 0.1);
        lastTime = System.currentTimeMillis();
    }

    public void driveStraightInit() {
        this.getPIDController().reset();
        this.getPIDController().enable();
    }

    public void driveStraight(double speed, double heading) {
        this.setSetpoint(heading);

        double leftSpeed = speed;
        double rightSpeed = speed - pidOutput;
        if (rightSpeed > 1d || rightSpeed < -1d) {
            leftSpeed = speed + pidOutput;
            rightSpeed = speed;
        }

        rawTankDrive(leftSpeed, rightSpeed);
    }

    public void driveStraightEnd() {
        this.getPIDController().reset();
        pidOutput = 0;
//        SmartDashboard.putNumber("Drive PID output", pidOutput);
//        SmartDashboard.putNumber("Drive PID error", this.getPIDController().getError());
    }

    public void tankDrive(double leftPower, double rightPower) {
        if (RATE_LIMITING_ENABLED) {
            long currentTime = System.currentTimeMillis();
            // If the robot is disabled and then enabled we don't want this value to be to large
            double maxChange = Preferences.getInstance().getDouble(PreferenceKeys.MAX_ACCEL, DEFAULT_MAX_CHANGE);
            double maxPowerDifference = maxChange * MathHelper.min((currentTime - lastTime), 100);
            lastTime = currentTime;
            if ((Math.abs(leftPower - lastLeftPower) > maxPowerDifference)) {
                double leftPowerChangeSign = ((leftPower - lastLeftPower) < 0.0) ? -1 : 1;
                leftPower = lastLeftPower + (maxPowerDifference * leftPowerChangeSign);
            }
            if ((Math.abs(rightPower - lastRightPower) > maxPowerDifference)) {
                double rightPowerChangeSign = ((rightPower - lastRightPower) < 0.0) ? -1 : 1;
                rightPower = lastRightPower + (maxPowerDifference * rightPowerChangeSign);
            }
        }
        
        rawTankDrive(leftPower, rightPower);
    }

    public void rawTankDrive(double leftPower, double rightPower) {
        lastLeftPower = leftPower;
        lastRightPower = rightPower;
        
        leftMotor1.set(-leftPower);
        leftMotor2.set(-leftPower);
        rightMotor1.set(rightPower);
        rightMotor2.set(rightPower);
    }
    
    public void stop() {
        try {
            while (Math.abs(lastLeftPower) > 0.2 || Math.abs(lastRightPower) > 0.2) {
                tankDrive(0, 0);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
        }

        leftMotor1.set(0);
        leftMotor2.set(0);
        rightMotor1.set(0);
        rightMotor2.set(0);
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

    public void resetGyro() {
        gyro.reset();
    }

    public void setGyroSensitivity(double k) {
        gyro.setSensitivity(k);
    }

    public static void resetRightEncoder() {
        rightQuadrature.reset();
    }

    public static void resetLeftEncoder() {
        leftQuadrature.reset();
    }

    public static double getEncoderDistance() {
        return MathHelper.max(Math.abs(leftQuadrature.getDistance()), Math.abs(rightQuadrature.getDistance()));
    }
    
    public static double getLeftQuadratureDistance() {
        return leftQuadrature.getDistance();
    }
    
    public static double getRightQuadratureDistance() {
        return rightQuadrature.getDistance();
    }
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new OperatorDriveCommand());
    }

    protected double returnPIDInput() {
        return getGyroAngle();
    }

    protected void usePIDOutput(double d) {
//        SmartDashboard.putNumber("Drive PID output", d);
//        SmartDashboard.putNumber("Drive PID error", this.getPIDController().getError());
        pidOutput = d;
    }
}
