package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Ascends the Climber
 * @author Charles, Patrick, Jared
 */
public class ClimbCommand extends Command
{
    private double climbSpeed;
    private OI oi = Robot.oi;
    private long timeOfExecution ;
    private static final long TIME_OF_EXECUTION = 10000;
    
    public ClimbCommand(double climbSpeed){
        this.climbSpeed = climbSpeed; 
        
        requires(Robot.climber);
        
}
    protected void initialize() {
        Robot.climber.stop();
        timeOfExecution = System.currentTimeMillis();
    }

    /**
     *the fire was red
     */
    protected void execute() 
    {
        
        if ( System.currentTimeMillis() - timeOfExecution > TIME_OF_EXECUTION)
        {
            Robot.climber.setClimberMotorPower(climbSpeed);
//            oi.getleftJoystick().getButton(); 
        }
    }

    /**
     *far over
     * the misty mountain cold
     * 
     * @return
     */
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
