package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.PIDShooter;
import org.usfirst.frc948.NRGRobot2013.subsystems.RawShooter;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
/**
 *
 * @author Kevin & Patrick
 */
public class OperatorShooterCommand extends Command {
    // http://lcec.us/javadoc/edu/wpi/first/wpilibj/command/Command.html
    
    private static double kDefaultShootP = 0.01;
    private static double kDefaultShootI = 0.00;
    private static double kDefaultShootD = 0.0;
    
    //
    private static double kDefaultAngleP = 0.01;
    private static double kDefaultAngleI = 0.0;
    private static double kDefaultAngleD = 0.0;
    
    private double shooterCurrentValue;
    private double power;


    public OperatorShooterCommand() {
    }

     protected void initialize()
     {
         
     }
     
    protected void execute()
    {
        shooterCurrentValue = OI.getRawShootSpeed();

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.rawShooter.stop();
        RobotMap.shooterMotor.set(0.0);
    }

    protected void interrupted() {
        end();
    }
}