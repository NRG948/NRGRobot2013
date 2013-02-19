package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.ShooterSpeed;

/**
 *
 * @author irving
 */
public class Shooter extends Subsystem {
    
    private double currentMotorPower = 0.0;
    private double desiredRPM = 0.0;
    
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ShooterSpeed());
    }
    
    public void setDesiredRPM(double rpm) {
        desiredRPM = rpm;
    }
    
    public double getDesiredRPM() {
        return desiredRPM;
    }
    
    public void setShooterMotorPower(double power) {
        currentMotorPower = power;
        RobotMap.shooterMotor.set(-power);
    }
    
    public double getCurrentMotorPower() {
        return currentMotorPower;
    }
    
}
