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
public class ClimberCommand extends Command
{
    private double rightJostickClimbValue;
    private double leftJoystickClimbValue;
    private Joystick.ButtonType climbButt;
    protected void initialize() 
    {
        requires(Robot.climber);
        //Climber.reset();
    }

    protected void execute() 
    {
        rightJostickClimbValue = 1;//Robot.oi.rightJoystick.getButton(Joystick.ButtonType);
        leftJoystickClimbValue = 2;//Robot.oi.leftJoystick.get
        
        Robot.climber.turnClockwise();
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
