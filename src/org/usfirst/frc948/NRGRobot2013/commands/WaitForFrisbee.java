package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Charles
 */
public class WaitForFrisbee extends Command {

    private boolean previousFrisbeeLoaded;
    private long end;
    
    public WaitForFrisbee() {
        requires(Robot.shooter);
        requires(Robot.discMagazine);
    }

    protected void initialize() {
        end = 0;
        previousFrisbeeLoaded = false;
    }

    protected void execute() {
        boolean currentFrisbeeLoaded = Robot.discMagazine.frisbeeLoaded();
        
        if (!previousFrisbeeLoaded && currentFrisbeeLoaded) {
            end = System.currentTimeMillis() + 500;
        }
        
        previousFrisbeeLoaded = currentFrisbeeLoaded;
    }

    protected boolean isFinished() {
        return previousFrisbeeLoaded && System.currentTimeMillis() >= end;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
