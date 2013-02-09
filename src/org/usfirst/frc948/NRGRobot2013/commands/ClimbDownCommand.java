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
 * @author Charles
 */
public class ClimbDownCommand extends Command 
{
    private double climbSpeed;
    protected void initialize() 
    {
        requires(Robot.climber);
    }

    protected void execute() 
    {
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end()
    {
    }

    protected void interrupted() 
    {
    }
    
}
