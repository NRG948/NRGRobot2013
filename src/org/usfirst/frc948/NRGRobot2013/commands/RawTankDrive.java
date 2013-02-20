package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Command for driving robot with raw motor power on each side.
 *
 * @author irving
 */
public class RawTankDrive extends Command {

    private final double leftSpeed;
    private final double rightSpeed;
    private final long length;
    private long endTime;
    
    public RawTankDrive(double speed, long milliseconds) {
        requires(Robot.drive);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.length = milliseconds;
    }
    
    public RawTankDrive(double leftSpeed, double rightSpeed, long milliseconds) {
        requires(Robot.drive);
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.length = milliseconds;
    }
    
    protected void initialize() {
        endTime = System.currentTimeMillis() + length;
    }

    protected void execute() {
        Robot.drive.rawTankDrive(leftSpeed, rightSpeed);
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
        Robot.drive.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
