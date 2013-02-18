package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author irving
 */
public class SetShooterSpeed extends Command {
    
    private double speed;
    private long overRevEnd;
    
    public SetShooterSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooter);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        overRevEnd = (long) (System.currentTimeMillis() + 5 * speed * 1000);
        Robot.shooter.setOverRev(Preferences.getInstance().getDouble(PreferenceKeys.OVER_REV_FACTOR, Shooter.DEFAULT_OVER_REV));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.shooter.setSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= overRevEnd;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.shooter.setOverRev(1.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
