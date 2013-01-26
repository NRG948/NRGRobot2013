package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Kevin & Patrick
 */
public class OperatorShooterCommand extends Command {
    // http://lcec.us/javadoc/edu/wpi/first/wpilibj/command/Command.html

    
    
    public OperatorShooterCommand() {
        requires(Robot.shooter);
    }

    protected void initialize() {
        
    }

    protected void execute() {
        Robot.shooter.setSpeed(OI.getRawShootSpeed());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        RobotMap.shootershootMotor.set(0.0);
    }

    protected void interrupted() {
        RobotMap.shootershootMotor.set(0.0);
    }
}