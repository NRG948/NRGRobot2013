package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class SetShooterOverRev extends Command {
    
    private final double factor;
    
    public SetShooterOverRev(double factor) {
        this.factor = factor;
    }

    protected void initialize() {
    }

    protected void execute() {
        Debug.println("[SetShooterOverRev] " + factor);
        Robot.shooter.setOverRev(factor);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    
}
