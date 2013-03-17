package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * Command to shoot a frisbee.
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command {

    public static final int DELAY = 250;
    
    private long endTime;
    
    public ReleaseFrisbeeCommand() {
        requires(Robot.discMagazine);
    }

    protected void initialize() {
        endTime = (long) (System.currentTimeMillis() + Preferences.getInstance().getDouble(PreferenceKeys.SHOOT_DELAY, DELAY));
    }

    protected void execute() {
        Robot.discMagazine.openPiston();
        Robot.shooter.setOverRev(Preferences.getInstance().getDouble(PreferenceKeys.OVER_REV_FACTOR, Shooter.DEFAULT_OVER_REV));
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
        Robot.shooter.setOverRev(1.0);
        Robot.discMagazine.closePiston();
    }

    protected void interrupted() {
        end();
    }
}
