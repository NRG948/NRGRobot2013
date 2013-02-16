/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 *
 * @author Sean
 */
public class ClimbStopCommand extends Command{

    protected void initialize() {
        requires(Robot.climber);
    }

    protected void execute() {
       Robot.climber.stop();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        Robot.climber.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
