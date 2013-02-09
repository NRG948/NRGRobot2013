/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.Climber;
/**
 * Descends the Climber
 * @author Charles
 */
public class ClimbDownCommand extends Command 
{
    private double climbSpeed = 0.5;
    protected void initialize() {
        //initialize
        requires(Robot.climber);
        
    }

    protected void execute() {
        Robot.climber.turnCounterClockwise(climbSpeed);
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end()
    {
        Robot.climber.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
