package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author Kevin & Patrick
 */
public class OperatorShooterSpeed extends Command {

    private final double maxRpm = 3400;
    private boolean usingPID = false; 
    
    public OperatorShooterSpeed() {
        requires(Robot.shooter);
    }

    protected void initialize() {
    }

    protected void execute() {
        double speedSlider = OI.getRawShootSpeed();
        
        if (Robot.oi.shooterUsePID()) {
            //if using PID for the first time, reset PID
            if(!usingPID) {
                Robot.shooter.reset();
                usingPID = true; 
            }
            SmartDashboard.putNumber("target RPM", speedSlider * maxRpm);
            Robot.shooter.setDesiredRPM(speedSlider * maxRpm);
        } else {
            SmartDashboard.putNumber("manual shoot speed", speedSlider);
            Robot.shooter.setRawPower(speedSlider);
            usingPID = false;
        }
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