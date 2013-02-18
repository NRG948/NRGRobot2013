package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Climber;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * Ascends the Climber
 *
 * @author Charles, Patrick, Jared
 */
public class ClimbCommand extends Command {

    private int direction;
    
    public ClimbCommand(int direction) {
        this.direction = direction;
        requires(Robot.climber);
    }

    protected void initialize() {
        Robot.climber.stop();
    }

    protected void execute() {
        Robot.climber.setClimberMotorPower(direction * Preferences.getInstance().getDouble(PreferenceKeys.CLIMBER_POWER, Climber.DEFAULT_POWER));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.climber.stop();
    }

    protected void interrupted() {
        end();
    }
}
