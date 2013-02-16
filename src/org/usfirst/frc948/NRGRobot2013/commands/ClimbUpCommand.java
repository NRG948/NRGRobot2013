package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Ascends the Climber
 * @author Charles, Patrick, Jared
 */
public class ClimbUpCommand extends Command
{
    private double climbSpeed;
    private OI oi = Robot.oi;
    
    public ClimbUpCommand(double climbSpeed){
        this.climbSpeed = climbSpeed; 
        requires(Robot.climber);
        
}
    protected void initialize() {
        requires(Robot.climber);
        Robot.climber.stop();
    }

    protected void execute() 
    {
        Robot.climber.setClimberMotorPower(climbSpeed);
        //oi.getleftJoystick().getButton();
        
    }

    protected boolean isFinished() 
    {
        return (Robot.oi.rightJoyBtn11.get());
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