package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author irving
 */
public class DriveInterrupt extends Command {
    
    public DriveInterrupt() { requires(Robot.drive); }
    protected void initialize() {}
    protected void execute() {}
    protected boolean isFinished() { return true; }
    protected void end() {}
    protected void interrupted() {}
    
}
