package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command {

    public static final int DEFAULT_DELAY = 1000;
    private long endTime;

    protected void initialize() {
        endTime = (long) (System.currentTimeMillis() + Preferences.getInstance().getDouble(PreferenceKeys.SHOOT_DELAY, DEFAULT_DELAY));
    }

    protected void execute() {
        Robot.discMagazine.openPiston();
        Robot.shooter.setOverRev(Preferences.getInstance().getDouble(PreferenceKeys.OVER_REV_FACTOR, Shooter.DEFAULT_OVER_REV));
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
        Robot.discMagazine.closePiston();
        Robot.shooter.setOverRev(1.0);
    }

    protected void interrupted() {
        end();
    }
}
