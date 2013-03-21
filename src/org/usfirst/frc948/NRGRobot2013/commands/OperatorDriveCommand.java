package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.IOperatorInterface;
/**
 * Default command for Drive subsystem.
 * 
 * @author hoileung
 */
public class OperatorDriveCommand extends Command {

    private static final double CLOSE_TO_ZERO = 0.08;
    
    private static final double DRIVE_SLOW_FACTOR = 0.5;
    private static final double DRIVE_NORMAL_FACTOR = 1.0;
    
    private IOperatorInterface oi = Robot.oi;
    
    private boolean prevDriveStraight = false;
    private boolean currDriveStraight = false;
    
    private double driveStraightHeading;
    
    private boolean prevDriveSlow = false;
    private boolean currDriveSlow = false;
    
    private double driveFactor = DRIVE_NORMAL_FACTOR;

    public OperatorDriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
        // If there is a sudden change in the y value of the joystick,
        // the average between the current motor speed and the targeted motor
        // speed will be calculated in order to prevent damage to the motors.
        double currentLeftJoystickYValue = -oi.getleftJoystick().getY();
        double currentRightJoystickYValue = -oi.getrightJoystick().getY();

        prevDriveStraight = currDriveStraight;
        currDriveStraight = oi.getDriveStraight();

        prevDriveSlow = currDriveSlow;
        currDriveSlow = oi.getDriveSlow();
        
        if (currDriveSlow) {
            if (!prevDriveSlow) {
                Debug.println("[OperatorDriveCommand] Entered slow drive mode");
                driveFactor = DRIVE_SLOW_FACTOR;
            }
        } else {
            if (prevDriveSlow) {
                Debug.println("[OperatorDriveCommand] Exited drive slow mode");
                driveFactor = DRIVE_NORMAL_FACTOR;
            }
        }

        if (currDriveStraight) {
            if (!prevDriveStraight) {
                Debug.println("[OperatorDriveCommand] Entered drive straight mode");
                driveStraightHeading = RobotMap.drivegyro.getAngle();
                Robot.drive.driveOnHeadingInit();
            }

            double speed = Math.abs(currentRightJoystickYValue) < CLOSE_TO_ZERO ? 0 : currentRightJoystickYValue;

            Robot.drive.driveOnHeading(speed * driveFactor, driveStraightHeading);
        } else {
            if (prevDriveStraight) {
                Debug.println("[OperatorDriveCommand] Exited drive straight mode");
                Robot.drive.driveOnHeadingEnd();
            }

            double leftMotorSpeed;
            double rightMotorSpeed;

            if (Math.abs(currentLeftJoystickYValue) < CLOSE_TO_ZERO) {
                leftMotorSpeed = 0;
            } else {
                leftMotorSpeed = currentLeftJoystickYValue;
            }

            if (Math.abs(currentRightJoystickYValue) < CLOSE_TO_ZERO) {
                rightMotorSpeed = 0;
            } else {
                rightMotorSpeed = currentRightJoystickYValue;
            }

            Robot.drive.tankDrive(leftMotorSpeed * driveFactor, rightMotorSpeed * driveFactor);
        }
        
        Robot.drive.setDesiredHeading(RobotMap.drivegyro.getAngle());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drive.tankDrive(0.0, 0.0);
    }

    protected void interrupted() {
        end();
    }
}
