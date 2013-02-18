package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Kevin & Patrick
 */
public class OperatorShooterCommand extends Command {

    public OperatorShooterCommand() {
        requires(Robot.shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        double shootSpeed = OI.getRawShootSpeed();
        SmartDashboard.putNumber("manual shoot speed", shootSpeed);
        Robot.shooter.setSpeed(shootSpeed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
}