package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Command to set desired heading of Drive subsystem.
 *
 * @author holeung
 */
public class SetDesiredHeading extends Command {

    private double heading;

    public SetDesiredHeading(double heading) {
        this.heading = heading;
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.drive.setDesiredHeading(heading);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
