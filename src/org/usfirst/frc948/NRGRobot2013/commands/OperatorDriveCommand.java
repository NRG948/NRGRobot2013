package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author hoileung
 */
public class OperatorDriveCommand extends Command {

    private static final double SUDDEN_CHANGE_THRESHOLD = 0.8;
    private double leftMotorSpeed;
    private double rightMotorSpeed;

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
        if (Math.abs((leftMotorSpeed - Robot.oi.getleftJoystick().getY())) >= SUDDEN_CHANGE_THRESHOLD) {
            leftMotorSpeed = MathHelper.average(leftMotorSpeed, Robot.oi.getleftJoystick().getY());
        } else {
            leftMotorSpeed = Robot.oi.getleftJoystick().getY();
        }

        if (Math.abs((rightMotorSpeed - Robot.oi.getrightJoystick().getY())) >= SUDDEN_CHANGE_THRESHOLD) {
            rightMotorSpeed = MathHelper.average(rightMotorSpeed, Robot.oi.getrightJoystick().getY());
        } else {
            rightMotorSpeed = Robot.oi.getrightJoystick().getY();
        }
        
        //
        if (Math.abs(leftMotorSpeed) < 0.08) {
            leftMotorSpeed = 0;
        }

        if (Math.abs(rightMotorSpeed) < 0.08) {
            rightMotorSpeed = 0;
        }

        Robot.drive.tankDrive(leftMotorSpeed, rightMotorSpeed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drive.tankDrive(0.0, 0.0);
    }

    protected void interrupted() {
        Robot.drive.tankDrive(0.0, 0.0);
    }
}
