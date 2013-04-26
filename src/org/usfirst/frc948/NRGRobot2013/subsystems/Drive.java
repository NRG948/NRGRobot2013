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

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * @author Charles, Jared
 */
public class Drive extends PIDSubsystem {

    private SpeedController leftMotor1 = RobotMap.driveleftMotor1;
    private SpeedController leftMotor2 = RobotMap.driveleftMotor2;
    private SpeedController rightMotor1 = RobotMap.driverightMotor1;
    private SpeedController rightMotor2 = RobotMap.driverightMotor2;
    private Encoder leftQuadrature = RobotMap.driveleftQuadrature;
    private Encoder rightQuadrature = RobotMap.driverightQuadrature;
    private Gyro gyro = RobotMap.drivegyro;
    
    private final PIDController pid;
    private static final double PID_MIN_OUTPUT = 0.08;
    private static final double PID_MAX_OUTPUT = 0.2;
    private static final double PID_PERIOD = 0.05;
    public static final double kDefaultP = 0.01;
    public static final double kDefaultI = kDefaultP*2*PID_PERIOD;
    public static final double kDefaultD = 0.005;
    
    private double pidOutput;
    private double desiredHeading;

    private boolean RATE_LIMITING_ENABLED = true;
    private long lastTime;
    private double lastLeftPower = 0.0;
    private double lastRightPower = 0.0;
    public static final double DEFAULT_MAX_ACCEL = 0.0025;    // Max drive power change per msec

    public Drive() {
        super("DrivePID", kDefaultP, kDefaultI, kDefaultD, 0.0, PID_PERIOD);
        pid = this.getPIDController();
        lastTime = System.currentTimeMillis();
    }

    public void driveOnHeadingInit() {
        pid.reset();
        double p = Preferences.getInstance().getDouble(PreferenceKeys.DRIVE_P, kDefaultP);
        double i = Preferences.getInstance().getDouble(PreferenceKeys.DRIVE_I, kDefaultI);
        double d = Preferences.getInstance().getDouble(PreferenceKeys.DRIVE_D, kDefaultD);
        pid.setPID(p, i, d);
        Debug.println("[Drive] driveStraightInit | P:" + p + " I:" + i + " D:" + d);
        pid.setOutputRange(-PID_MAX_OUTPUT, PID_MAX_OUTPUT);
        pid.enable();
    }

    public void driveOnHeading(double power, double heading) {
        this.setSetpoint(heading);
        // Scale the PIDController output range based on the size of the current heading error in degrees
        double error = heading - Robot.positionTracker.getHeading();
        double outputRange = MathHelper.clamp(PID_MIN_OUTPUT + (Math.abs(error)/15)*(PID_MAX_OUTPUT-PID_MIN_OUTPUT), 0, PID_MAX_OUTPUT);
        pid.setOutputRange(-outputRange, outputRange);
        pidOutput = MathHelper.clamp(pidOutput, -outputRange, outputRange); // in case the last pidOutput is out of range
        
        // Adjust left/right drive power so that we follow the heading but don't exceed the max specified power
        double leftPower, rightPower;
        if (pidOutput >= 0.0) {
            leftPower = power;
            rightPower = power - pidOutput;
        } else {
            leftPower = power + pidOutput;
            rightPower = power;
        }
        rawTankDrive(leftPower, rightPower);

//        SmartDashboard.putData("DrivePID", pid);
//        SmartDashboard.putNumber("Drive PID output", pidOutput);
//        SmartDashboard.putNumber("Drive PID error", pid.getError());
//        SmartDashboard.putNumber("Drive PID Lpower", leftPower);
//        SmartDashboard.putNumber("Drive PID Rpower", rightPower);
    }

    public void driveOnHeadingEnd() {
        pid.reset();
        pidOutput = 0;
    }

    public void tankDrive(double leftPower, double rightPower) {
        if (RATE_LIMITING_ENABLED) {
            long currentTime = System.currentTimeMillis();
            // If the robot is disabled and then enabled we don't want this value to be to large
            double maxChange = Preferences.getInstance().getDouble(PreferenceKeys.MAX_ACCEL, DEFAULT_MAX_ACCEL);
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

        rawStop();
    }
    
    public void rawStop() {
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

    public void setGyroSensitivity(double k) {
        gyro.setSensitivity(k);
    }

    public void resetRightEncoder() {
        rightQuadrature.reset();
    }

    public void resetLeftEncoder() {
        leftQuadrature.reset();
    }

    public double getEncoderDistance() {
        return MathHelper.max(Math.abs(leftQuadrature.getDistance()), Math.abs(rightQuadrature.getDistance()));
    }
    
    public double getLeftQuadratureDistance() {
        return leftQuadrature.getDistance();
    }
    
    public double getRightQuadratureDistance() {
        return rightQuadrature.getDistance();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new OperatorDriveCommand());
    }

    protected double returnPIDInput() {
        return getGyroAngle();
    }

    protected void usePIDOutput(double d) {
        pidOutput = d;
    }
}
