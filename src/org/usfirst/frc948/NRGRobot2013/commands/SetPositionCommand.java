package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 *
 * @author Sean + Charles
 */
public class SetPositionCommand extends Command{
    
    private double DesiredxPos = 0;
    private double DesiredyPos = 0;
    
    public SetPositionCommand(double a, double b) {
        DesiredxPos = a;
        DesiredyPos = b;
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.positionTracker.setPosition(DesiredxPos, DesiredyPos);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
