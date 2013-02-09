/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Climber;

/**
 *
 * @author Charles, Patrick, Jared
 */
public class ClimbUpCommand extends Command
{
    private double climbSpeed;
    
    protected void initialize() 
    {
        requires(Robot.climber);
        //Climber.reset();
    }

    protected void execute() 
    {
        
        
        Robot.climber.turnClockwise(climbSpeed);
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end()
    {
        Robot.climber.stop();
    }

    protected void interrupted() 
    {
        end();
    }
}
