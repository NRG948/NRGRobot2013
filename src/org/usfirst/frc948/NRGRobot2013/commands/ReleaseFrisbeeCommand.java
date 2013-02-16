package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command {

    private static final int DELAY = 1000;
    
    public static int count = 0;
    private long endTime;

    protected void initialize() {
        endTime = System.currentTimeMillis() + DELAY;
        Robot.discMagazine.openPiston();
        count++;
    }

    protected void execute() {}

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
        Robot.discMagazine.closePiston();
    }

    protected void interrupted() {
        end();
    }

}
