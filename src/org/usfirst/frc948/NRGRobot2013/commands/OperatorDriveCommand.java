package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author hoileung
 */
public class OperatorDriveCommand extends Command {

    private static final double CLOSE_TO_ZERO = 0.08;
    private static final double DRIVE_SLOW_FACTOR = 0.5;
    private static final double NORMAL_DRIVE = 1.0;
    private OI oi = Robot.oi;
    
    private boolean prevDriveStraight = false;
    private boolean currDriveStraight = false;
    
    private double driveStraightHeading;
    
    private boolean prevDriveSlow = false;
    private boolean currDriveSlow = false;
    
    private double driveFactor = NORMAL_DRIVE;

    public OperatorDriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
        //If there is a sudden change in the y value of the joystick,
        //the average between the current motor speed and the targeted motor
        //speed will be calculated in order to prevent damages to the motors.
        double currentLeftJoystickYValue = -oi.getleftJoystick().getY();
        double currentRightJoystickYValue = -oi.getrightJoystick().getY();

        prevDriveStraight = currDriveStraight;
        currDriveStraight = oi.getDriveStraight();
        
        prevDriveSlow = currDriveSlow;
        currDriveSlow = oi.getDriveSlow();
        if(currDriveSlow)
        {
            if(!prevDriveSlow)
            {
                Debug.println(Debug.DRIVE, "Entered slow drive mode");
                driveFactor = DRIVE_SLOW_FACTOR;
            }  
        }
        else {
            if(prevDriveSlow){
                Debug.println(Debug.DRIVE, "Exited drive slow mode");
                driveFactor = NORMAL_DRIVE;
            }
        }
        
        if (currDriveStraight) {
            if (!prevDriveStraight) {
                Debug.println(Debug.DRIVE, "Entered drive straight mode");
                driveStraightHeading = RobotMap.drivegyro.getAngle();
                Robot.drive.driveStraightInit();
            }
            
            double speed = Math.abs(currentRightJoystickYValue) < CLOSE_TO_ZERO ? 0 : currentRightJoystickYValue;
            
            Robot.drive.driveStraight(speed * driveFactor, driveStraightHeading);
        } else {
            if (prevDriveStraight) {
                Debug.println(Debug.DRIVE, "Exited drive straight mode");
                Robot.drive.driveStraightEnd();
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
