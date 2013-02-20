package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Sean
 */
public class DriveStraightTime extends Command {

    private double speed = 0;
    private long time = 0;
    private long endTime = 0;

    /**
     * @param speed power to drive at [-1, 1]
     * @param time time to drive in milliseconds
     */
    public DriveStraightTime(double speed, long time) {
        requires(Robot.drive);
        this.speed = speed;
        this.time = time;
    }

    protected void initialize() {
        Robot.drive.driveStraightInit();
        endTime = System.currentTimeMillis() + time;
    }

    protected void execute() {
        Robot.drive.driveStraight(speed, Robot.drive.getDesiredHeading());
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
        Robot.drive.driveStraightEnd();
        Robot.drive.stop();
    }

    protected void interrupted() {
        end();
    }
}
