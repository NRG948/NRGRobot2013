package org.usfirst.frc948.NRGRobot2013.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
/**
 *
 * @author Kevin
 */
public class OperatorShooterCommand extends Command
{
    // http://lcec.us/javadoc/edu/wpi/first/wpilibj/command/Command.html
    private double shooterCurrentValue;
    public OperatorShooterCommand()
    {
        requires(Robot.shooter);
    }
     protected void initialize()
     {
         this.requires(Robot.shooter);
         shooterCurrentValue = 0.0;
     }
     
    protected void execute()
    {
        shooterCurrentValue = OI.getRawShootSpeed();
        
        RobotMap.shootershootMotor.set(shooterCurrentValue);
    }
    
    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
        RobotMap.shootershootMotor.set(0.0);
    }

    protected void interrupted()
    {
        RobotMap.shootershootMotor.set(0.0);
    }
    
}