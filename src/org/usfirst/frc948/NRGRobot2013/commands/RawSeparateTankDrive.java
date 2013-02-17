package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author irving
 */
public class RawSeparateTankDrive extends Command {

    private double leftSpeed;
    private double rightSpeed;
    private long time;
    private long start;
    
    public RawSeparateTankDrive(double leftSpeed, double rightSpeed, long milliseconds) {
        requires(Robot.drive);
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.time = milliseconds;
    }
    
    protected void initialize() {
        start = System.currentTimeMillis();
    }

    protected void execute() {
        Robot.drive.rawTankDrive(leftSpeed, rightSpeed);
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
