/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Sean
 */

public class DriveStraightTime extends Command{
    double speed = 0;
    long time = 0; //in milliseconds
    long endTime = 0; 
    
    public DriveStraightTime(double speed, long time){
        requires(Robot.drive);
        this.speed=speed;
        this.time =time;  
    }

    protected void initialize() {
        Robot.drive.driveStraightInit();
        Robot.drive.resetLeftEncoder();
        Robot.drive.resetRightEncoder();
        endTime = System.currentTimeMillis() + time;
    }

    protected void execute() {
        Robot.drive.driveStraight(speed, Robot.drive.getDesiredHeading());
    }

    protected boolean isFinished() {
        return (System.currentTimeMillis() >endTime);
        
    }

    protected void end() {
        Robot.drive.stop();
    }

    protected void interrupted() {
        Robot.drive.stop();
    }
    
}
