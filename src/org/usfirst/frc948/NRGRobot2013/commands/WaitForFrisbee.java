package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Charles
 */
public class WaitForFrisbee extends Command {

    public WaitForFrisbee() {
        requires(Robot.shooter);
        requires(Robot.discMagazine);
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.discMagazine.frisbeeLoaded();
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
