package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class ToggleCameraWrite extends Command {

    protected void initialize() {
        
    }

    protected void execute() {
        boolean set = !Robot.camera.getImageWrite();
        Robot.camera.setImageWrite(set);
        Debug.println("[ToggleCameraWrite] image write set to " + set);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
