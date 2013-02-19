/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Charles
 */
public class WaitForMinRPM extends Command{
    private double rpm;
    public WaitForMinRPM(double rpm)
    {
        this.rpm = rpm;
        //requires(Robot.shooter);
    }
    protected void initialize() {
    }

    protected void execute() {
        Watchdog.getInstance().feed();
    }

    protected boolean isFinished() {
        return RobotMap.shooterQuadrature.getRPM() > rpm;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
