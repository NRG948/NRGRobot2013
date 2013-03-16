package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author irving
 */
public class DriveToXYParams extends Command {

    protected void initialize() {
        
    }

    protected void execute() {
        double power = Preferences.getInstance().getDouble(PreferenceKeys.DOUBLE_PARAM1, 0.5);
        double x = Preferences.getInstance().getDouble(PreferenceKeys.DOUBLE_PARAM2, 5.0);
        double y = Preferences.getInstance().getDouble(PreferenceKeys.DOUBLE_PARAM3, 5.0);
        Debug.println("[DriveToXYParams] running DriveToXY(" + power + ", " + x + ", " + y + ")");
        (new DriveToXY(power, x, y)).start();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
