/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author ForisKuang
 */
public class CalibrateAzimuth extends Command {

    private boolean unknownState;
    private boolean maxAndMin;
    private boolean downMode;
    private boolean done;
    private static final double SPEED = 0.3;
    private int maxSetpoint;
    DigitalInput minAngleSwitch = RobotMap.minAngleSwitch;
    DigitalInput maxAngleSwitch = RobotMap.maxAngleSwitch;

    public CalibrateAzimuth(boolean maxAndMin) {
        requires(Robot.aimSystem);
        this.maxAndMin = maxAndMin;
    }

    protected void initialize() {
        downMode = true;
        done = false;
    }

    protected void execute() {
        if (downMode) {
            if (minAngleSwitch.get()) {
                RobotMap.shooterAngleMotor.set(0);
                RobotMap.azimuthQuadrature.reset();
                downMode = false;

            } else {
                RobotMap.shooterAngleMotor.set(-SPEED);
            }

        } else {
            if (maxAndMin) {
                if (maxAngleSwitch.get()) {
                    maxSetpoint = RobotMap.azimuthQuadrature.get();
                    SmartDashboard.putNumber("azimuth max", maxSetpoint);
                    done = true;
                } else {
                    RobotMap.shooterAngleMotor.set(SPEED);
                }
            }
            else
            {
                done = true;
            }
        }
    }

    protected boolean isFinished() {
        return done;
    }

    protected void end() {
        RobotMap.shooterAngleMotor.set(0);
    }

    protected void interrupted() {
        end();
    }
}
