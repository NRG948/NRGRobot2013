package org.usfirst.frc948.NRGRobot2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 * Descends the Climber
 * @author Charles
 */
public class ClimbDownCommand extends Command 
{
    private double climbSpeed = 0.5;
    private double timeOfExecution = 10; //in seconds 
    
    public ClimbDownCommand(){
        
    }
        
    protected void initialize() {
        
        //initialize
        requires(Robot.climber);
        
        
    }

    /**
     *
     */
    protected void execute() {
        Robot.climber.setClimberMotorPower(-climbSpeed);
        
    }

    protected boolean isFinished() {
        return false;
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
