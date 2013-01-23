/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author Patrick Lin & Sean Yu
 */
public class DriveStraightCommand extends Command {
    
    private static final double powerRange = 0.05d;
    private double power;
    private double time;
    Timer timer = new Timer();
    
    public DriveStraightCommand(double power, double time) {
        
        Robot.drive.resetGyro();
        
        this.power = power;
        this.time = time;
        
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        timer.start();
        
        while(timer.get() < time) {
            
        }
        
        final double maxSpeed = power + powerRange;
        final double minSpeed = power - powerRange;
        
        if (Robot.drive.getGyroAngle() > 0){
            
        } else if (Robot.drive.getGyroAngle() < 0){
            
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
