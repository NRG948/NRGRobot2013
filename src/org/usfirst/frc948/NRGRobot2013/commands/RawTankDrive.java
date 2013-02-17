package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author irving
 */
public class RawTankDrive extends Command {

    private double speed;
    private long time;
    private long start;
    
    public RawTankDrive(double speed, long milliseconds) {
        requires(Robot.drive);
        this.speed = speed;
        this.time = milliseconds;
    }
    
    protected void initialize() {
        start = System.currentTimeMillis();
    }

    protected void execute() {
        Robot.drive.rawTankDrive(speed, speed);
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() - start >= time;
    }

    protected void end() {
        Robot.drive.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
