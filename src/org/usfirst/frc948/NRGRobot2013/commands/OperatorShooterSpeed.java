package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * Default command for Shooter subsystem.
 * 
 * @author Kevin & Patrick
 */
public class OperatorShooterSpeed extends Command {

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
            
            SmartDashboard.putNumber("PID target RPM", speedSlider * Shooter.MAX_RPM);
            Robot.shooter.setDesiredRPM(speedSlider * Shooter.MAX_RPM);
            Robot.shooter.setPidState(true);
        } else {
            double speed = speedSlider + Robot.oi.getShootTrim();
            SmartDashboard.putNumber("manual shoot speed", speed);
            Robot.shooter.setRawPower(speed);
            LCD.println(true, 6, "RAW:" + MathHelper.round(speed, 3) + " EST:" + MathHelper.round(MathHelper.PowerToRpm(speed), 0));
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