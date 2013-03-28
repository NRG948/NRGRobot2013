package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class WaitForAutoShootSwitch extends Command {
    
    public WaitForAutoShootSwitch() {
        requires(Robot.shooter);
        requires(Robot.discMagazine);
    }
    
    protected void initialize() {
        Debug.println("[WaitForAutoShootSwitch] waiting for auto shoot switch...");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.oi.autoShootEnabled();
    }

    protected void end() {
        Debug.println("[WaitForAutoShootSwitch] done waiting");
    }

    protected void interrupted() {
    }
    
}
