package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Empty as of RIGHT NOW, we have not determined our mechanism to lean forward
 * on the edge
 * UPDATE: PISTON to pump forward
 * @author Charles
 */
public class TiltCommand extends Command {

    private boolean tilt;
    private boolean isFinished;
    
    public TiltCommand(boolean tilt) {
        this.tilt = tilt;
    }
    
    protected void initialize() {
        isFinished = false;
    }

    protected void execute() {
        if (tilt) {
            Robot.climber.activateTilt();
        } else {
            Robot.climber.disengage();
        }
        
        isFinished = true;
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
