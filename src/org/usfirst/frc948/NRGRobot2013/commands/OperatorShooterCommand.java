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
        Robot.rawShooter.setSpeed(OI.getRawShootSpeed());
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