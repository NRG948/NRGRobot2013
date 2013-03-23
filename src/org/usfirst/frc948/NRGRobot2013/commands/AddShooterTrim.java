package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class AddShooterTrim extends Command {
    
    private final double trim;
    
    public AddShooterTrim(double trim) {
        this.trim = trim;
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        Robot.shooter.trimCenter += trim;
        Debug.println("[AddShooterTrim] " + trim + ", new center:" + Robot.shooter.trimCenter);
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
