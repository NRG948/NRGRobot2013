/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author hoileung
 */
public class DriveCommand extends Command {
    private double leftMotorSpeed;
    private double rightMotorSpeed;
    
    public DriveCommand(double leftMotorSpeed,double rightMotorSpeed)
    {
        this.leftMotorSpeed = leftMotorSpeed;
        this.rightMotorSpeed = rightMotorSpeed;
    }
    protected void initialize() {

    }

    protected void execute() 
    {
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
