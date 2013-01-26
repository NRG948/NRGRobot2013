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
public class DriveStraightDistance extends Command{
    double speed = 0.0;
    double finalDistance = 0; //in feet
    double distance = 0;
    public DriveStraightDistance(double speed, double distance){
        requires(Robot.drive);
        this.speed = speed;
        this.distance = distance;
    }
    protected void initialize() {
        finalDistance = Robot.drive.getEncoderDistance() + distance;
    }

    protected void execute() {
        Robot.drive.driveStraight(speed, Robot.drive.getDesiredHeading());
    }

    protected boolean isFinished() {
        return (Robot.drive.getEncoderDistance() > finalDistance);
    }

    protected void end() {
        Robot.drive.stop();
    }

    protected void interrupted() {
        Robot.drive.stop();
    }
    
}
