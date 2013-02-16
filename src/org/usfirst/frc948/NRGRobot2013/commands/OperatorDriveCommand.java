package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author hoileung
 */
public class OperatorDriveCommand extends Command {

    private static final double CLOSE_TO_ZERO = 0.08;
    private OI oi = Robot.oi;
    private double leftMotorSpeed;
    private double rightMotorSpeed;
    private double currentLeftJoystickYValue;
    private double currentRightJoystickYValue;

    public OperatorDriveCommand() {
        requires(Robot.drive);
    }

    protected void initialize() {

        leftMotorSpeed = 0.0;
        rightMotorSpeed = 0.0;
    }

    protected void execute() {
        //If there is a sudden change in the y value of the joystick,
        //the average between the current motor speed and the targeted motor
        //speed will be calculated in order to prevent damages to the motors.
        currentLeftJoystickYValue = -oi.getleftJoystick().getY();
        currentRightJoystickYValue = -oi.getrightJoystick().getY();

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

        Robot.drive.tankDrive(-leftMotorSpeed, -rightMotorSpeed);
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
