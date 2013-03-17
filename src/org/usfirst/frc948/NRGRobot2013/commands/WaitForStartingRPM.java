package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irving
 */
public class WaitForStartingRPM extends Command {

    private final double rpm;

    public WaitForStartingRPM(double rpm) {
        requires(Robot.shooter);
        this.rpm = rpm;
    }

    protected void initialize() {
        Debug.println("[WaitForStartingRPM]=" + rpm + " initializing with RPM=" + RobotMap.shooterQuadrature.averageRPM());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return RobotMap.shooterQuadrature.averageRPM() <= rpm + Robot.oi.getShootTrimRPM();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
