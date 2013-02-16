package org.usfirst.frc948.NRGRobot2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 * Descends the Climber
 * @author Charles
 */
public class ClimbStopCommand extends Command 
{
//    private double climbSpeed = 0.5;
//    private static final long TIME_OF_EXECUTION = 10000; //in seconds 
//    private long currentTime ;
    public ClimbStopCommand(){
        
    }
        
    protected void initialize() {
        
        //initialize
        requires(Robot.climber);
        Robot.climber.stop();
        
    }

    /**
     *
     */
    protected void execute() {
        Robot.climber.stop();   
    }

    protected boolean isFinished() {
        return true;
    }

    /**
     *
     */
    protected void end()
    {
        Robot.climber.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
