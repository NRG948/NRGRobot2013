package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
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
    
    public void releaseFrisbee() {
        try {
            Robot.discMagazine.setState(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}