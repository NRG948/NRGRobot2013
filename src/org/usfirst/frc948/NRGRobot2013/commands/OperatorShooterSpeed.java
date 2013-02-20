package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        System.out.println("[OperatorShooterSpeed] initializing");
    }

    protected void execute() {
        double speedSlider = Robot.oi.getRawShootSpeed();
        
        if (Robot.oi.shooterUsePID()) {
            //if using PID for the first time, reset PID
            if(!usingPID) {
                Robot.shooter.reset();
                usingPID = true;
                System.out.println("[OperatorShooterSpeed] switch to PID");
            }
            SmartDashboard.putNumber("PID target RPM", speedSlider * maxRpm);
            Robot.shooter.setDesiredRPM(speedSlider * maxRpm);
            Robot.shooter.setPidState(true);
        } else {
            SmartDashboard.putNumber("manual shoot speed", speedSlider);
            Robot.shooter.setRawPower(speedSlider);
            usingPID = false;
            Robot.shooter.setPidState(false);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        System.out.println("[OperatorShooterSpeed] ending");
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
    
}