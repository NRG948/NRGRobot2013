package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author irving
 */
public class TurnParams extends Command {

    protected void initialize() {
        
    }

    protected void execute() {
        double power = Preferences.getInstance().getDouble(PreferenceKeys.DOUBLE_PARAM1, 15.0);
        double degrees = Preferences.getInstance().getDouble(PreferenceKeys.DOUBLE_PARAM2, 0.4);
        Debug.println("[TurnParams] running Turn(" + degrees + ", " + power + ")");
        (new TurnCommand(degrees, power)).start();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
