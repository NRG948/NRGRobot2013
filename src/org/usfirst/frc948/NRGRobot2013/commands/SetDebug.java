package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class SetDebug extends Command {
    
    private final boolean set;
    
    public SetDebug(boolean set) {
        this.set = set;
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        if (set) {
            Debug.enable();
        } else {
            Debug.disable();
        }
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
