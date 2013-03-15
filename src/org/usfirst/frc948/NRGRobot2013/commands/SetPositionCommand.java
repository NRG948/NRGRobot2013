/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.utilities.PositionTracker;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 *
 * @author Sean + Charles
 */
public class SetPositionCommand extends Command{
    
    private static double DesiredxPos = 0;
    private static double DesiredyPos = 0;
    public SetPositionCommand(double a, double b){
        DesiredxPos = a;
        DesiredyPos = b;
    }

    protected void initialize() {
        Robot.positionTracker.init();
    }

    protected void execute() {
        Robot.positionTracker.setNewPosition(DesiredxPos, DesiredxPos);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
