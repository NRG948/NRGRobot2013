/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Patrick
 */
public class ReleaseFrisbeeCommand extends Command{

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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
