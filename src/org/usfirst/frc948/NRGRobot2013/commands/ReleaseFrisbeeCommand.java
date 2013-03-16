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

    public static final int INITIAL_DELAY = 250;
    public static final int SHOOT_DELAY = 500;
    
    private long closeTime;
    private long endTime;
    
    public ReleaseFrisbeeCommand() {
        requires(Robot.discMagazine);
    }

    protected void initialize() {
//        closeTime = (long) (System.currentTimeMillis() + Preferences.getInstance().getDouble(PreferenceKeys.SHOOT_DELAY, SHOOT_DELAY));
        closeTime = System.currentTimeMillis() + INITIAL_DELAY;
        endTime = closeTime + SHOOT_DELAY;
    }

    protected void execute() {
        long now = System.currentTimeMillis();
        
        if (now < closeTime) {
            Robot.discMagazine.openPiston();
            Robot.shooter.setOverRev(Preferences.getInstance().getDouble(PreferenceKeys.OVER_REV_FACTOR, Shooter.DEFAULT_OVER_REV));
        } else if (now < endTime) {
            Robot.discMagazine.closePiston();
            Robot.shooter.setOverRev(1.0);
        }
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
