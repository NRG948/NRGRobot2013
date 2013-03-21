package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 * Command to engage/disengage climber piston.
 * 
 * @author Charles
 */
public class TiltCommand extends Command {

    private final boolean tilt;
    
    public TiltCommand(boolean tilt) {
        this.tilt = tilt;
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        Debug.println("[TiltCommand] tilt:" + tilt);
        if (tilt) {
            Robot.climber.activateTilt();
        } else {
            Robot.climber.disengage();
        }
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
