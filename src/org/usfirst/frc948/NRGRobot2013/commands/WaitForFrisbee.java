package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

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
        Debug.println("[WaitForFrisbee] waiting for frisbee...");
    }

    protected void execute() {
        boolean currentFrisbeeLoaded = Robot.discMagazine.frisbeeLoaded();
        
        if (!previousFrisbeeLoaded && currentFrisbeeLoaded) {
            end = System.currentTimeMillis() + 500;
        }
        
        previousFrisbeeLoaded = currentFrisbeeLoaded;
    }

    protected boolean isFinished() {
        boolean finished = previousFrisbeeLoaded && System.currentTimeMillis() >= end;
        
        if (finished) {
            Debug.println("[WaitForFrisbee] done waiting");
            return true;
        }
        
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
