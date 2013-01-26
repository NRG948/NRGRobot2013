package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author irving
 */
public class ResetGyroCommand extends Command {

    private boolean isFinished = false;
    
    public ResetGyroCommand() {
        requires(Robot.drive);
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        Robot.drive.resetGyro();
        isFinished = true;
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
