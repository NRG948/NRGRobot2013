/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import java.lang.Math;

/**
 *
 * @author Sean
 */
public class DriveStraightDistance extends Command {

    double speed = 0.0;
    double finalDistance = 0; //in feet
    double distance = 0;
    double distanceRemaining;

    public DriveStraightDistance(double speed, double distance) {
        requires(Robot.drive);
        this.speed = speed;
        this.distance = distance;
    }

    protected void initialize() {
        Robot.drive.resetLeftEncoder();
        Robot.drive.resetRightEncoder();
        Robot.drive.driveStraightInit();
        finalDistance = Math.abs(Robot.drive.getEncoderDistance()) + distance;
    }

    protected void execute() {
        distanceRemaining = finalDistance - Math.abs(Robot.drive.getEncoderDistance());
        Robot.drive.driveStraight(MathHelper.clamp(distanceRemaining / 2, -Math.abs(speed), Math.abs(speed)), Robot.drive.getDesiredHeading());

    }

    protected boolean isFinished() {
        return (Math.abs(Robot.drive.getEncoderDistance()) >= finalDistance);
    }

    protected void end() {
        Robot.drive.stop();
    }

    protected void interrupted() {
        Robot.drive.stop();
    }
}
