package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * Command to read new gyro sensitivity from Preferences table.
 * 
 * @author irving
 */
public class ReadGyroSensitivity extends Command {

    protected void initialize() {
        
    }

    protected void execute() {
        double s = Preferences.getInstance().getDouble(PreferenceKeys.GYRO_SENSITIVITY, RobotMap.DEFAULT_GYRO_SENSITIVITY);
        Robot.drive.setGyroSensitivity(s);
        Debug.println(Debug.GYRO, "Read new gyro sensitivity: " + s);
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
