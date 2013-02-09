/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Climber;

/**
 *
 * @author Charles, Patrick, Jared
 */
public class ClimbUpCommand extends Command
{
    private double climbSpeed = 0.5;
    private OI oi = Robot.oi;
    protected void initialize() 
    {
        requires(Robot.climber);
        //Climber.reset();
    }

    protected void execute() 
    {
        Robot.climber.turnClockwise(climbSpeed);
        //oi.getleftJoystick().getButton();
        
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