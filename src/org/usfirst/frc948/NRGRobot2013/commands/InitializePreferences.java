package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author ALISHA
 */
public class InitializePreferences extends Command {
    
    protected void initialize() {
        
    }
    
    protected void execute() {
        Debug.println("[InitializePreferences]");
        Robot.initPreferences();
        Autonomous.initPreferences();
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
