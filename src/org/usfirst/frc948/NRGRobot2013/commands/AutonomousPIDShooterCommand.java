package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author holeung
 */
public class AutonomousPIDShooterCommand extends Command {

    private double angle;
    private double power;

    public AutonomousPIDShooterCommand(double angle, double power) {
        //requires(Robot.PIDShooter);
        this.angle = angle;
        this.power = power;
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.PIDShooter.getRobotShooterControl().changeAngle(angle);
        Robot.PIDShooter.getRobotShooterControl().changeSpeed(power);
    }

    protected boolean isFinished() {
        return ReleaseFrisbeeCommand.count >= 4;
    }

    protected void end() {
        Robot.PIDShooter.stop();
    }

    protected void interrupted() {
        end();
    }
}
