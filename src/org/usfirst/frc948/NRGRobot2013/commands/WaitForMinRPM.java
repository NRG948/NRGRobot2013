package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Charles
 */
public class WaitForMinRPM extends Command {

    private double rpm;

    public WaitForMinRPM(double rpm) {
        this.rpm = rpm;
    }

    protected void initialize() {
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return RobotMap.shooterQuadrature.averageRPM() >= rpm;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
