/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author ALISHA
 */
public class InitializePreferences extends Command {
    
    public InitializePreferences() {
        
    }
    
    protected void initialize() {
    
    }
    
    protected void execute() {
        Robot.initPreferences();
    }
    
    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {
        
    }
    
    protected void interrupted() {
        end();
    }
    
}
