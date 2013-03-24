package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
/**
 *
 * @author Sean + Charles
 */
public class SetPositionCommand extends Command {
    
    private final double x;
    private final double y;
    
    public SetPositionCommand(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected void initialize() {
    }

    protected void execute() {
        Debug.println("[SetPositionCommand] setting current position to (" + x + "," + y + ")");
        Robot.positionTracker.setPosition(x, y);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
