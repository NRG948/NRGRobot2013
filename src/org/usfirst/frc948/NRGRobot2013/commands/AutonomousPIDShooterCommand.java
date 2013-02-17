package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author holeung
 */
public class AutonomousPIDShooterCommand extends Command {

    //private double angle;
    private double power;

    public AutonomousPIDShooterCommand(double power) {
        requires(Robot.shooter);
        //requires(Robot.aimSystem);
        this.power = power;
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.shooter.setSpeed(power);
    }

    protected boolean isFinished() {
        return Robot.discMagazine.getCount() >= 4;
    }

    protected void end() {
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
}
