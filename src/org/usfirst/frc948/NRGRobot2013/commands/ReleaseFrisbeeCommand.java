package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command {

    public static int count = 0;
    private boolean finished;

    protected void initialize() {
        finished = false;
    }

    protected void execute() {
        releaseFrisbee();
        finished = true;
    }

    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

    private void releaseFrisbee() {
        try {
            Robot.discMagazine.releaseFrisbee();
            count++;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
