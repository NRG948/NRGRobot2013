package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Drive;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Sean
 */
public class DriveStraightDistance extends Command {

    private final double speed;
    private final double distance;

    public DriveStraightDistance(double speed, double distance) {
        requires(Robot.drive);
        this.speed = speed;
        this.distance = distance;
    }

    protected void initialize() {
        Drive.resetLeftEncoder();
        Drive.resetRightEncoder();
        Robot.drive.driveStraightInit();
    }

    protected void execute() {
        double distanceRemaining = distance - Drive.getEncoderDistance();
        if (speed > 0) {
            Robot.drive.driveStraight(MathHelper.clamp(distanceRemaining / 2, -Math.abs(speed), Math.abs(speed)),
                    Robot.drive.getDesiredHeading());
        } else if (speed < 0) {
            Robot.drive.driveStraight(MathHelper.clamp(-distanceRemaining / 2, -Math.abs(speed), Math.abs(speed)),
                    Robot.drive.getDesiredHeading());
        }

    }

    protected boolean isFinished() {
        return Drive.getEncoderDistance() >= distance;
    }

    protected void end() {
        Robot.drive.driveStraightEnd();
        Robot.drive.stop();
    }

    protected void interrupted() {
        end();
    }
}
