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
    private static final double SUDDEN_CHANGE_CONSTANT = 0.8;
    private OI oi;
    private double leftMotorSpeed;
    private double rightMotorSpeed;
    
    
    public OperatorDriveCommand(OI oi) {
        this.oi = oi;
    }
    
    protected void initialize() {
        this.requires(Robot.drive);
        leftMotorSpeed = 0.0;
        rightMotorSpeed = 0.0; 
    }

    protected void execute() 
    {
        //If there is a sudden change in the y value of the joystick,
        //the average between the current motor speed and the targeted motor
        //speed will be calculated in order to prevent damages to the motors. 
        if(Math.abs((leftMotorSpeed-oi.getleftJoystick().getY()))>= SUDDEN_CHANGE_CONSTANT)
        {
            leftMotorSpeed = MathHelper.average(leftMotorSpeed, oi.getleftJoystick().getY());
        }
        else
        {
        leftMotorSpeed = oi.getleftJoystick().getY();
        }
        
        if(Math.abs((rightMotorSpeed-oi.getrightJoystick().getY()))>= SUDDEN_CHANGE_CONSTANT)
        {
            rightMotorSpeed = MathHelper.average(rightMotorSpeed, oi.getrightJoystick().getY());
        }
        else
        {
        rightMotorSpeed = oi.getrightJoystick().getY();
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
