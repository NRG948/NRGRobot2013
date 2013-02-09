package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command {

    public static int count;

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    protected void releaseFrisbee() {
        try {
            Robot.discMagazine.releaseFrisbee();
            count++;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
