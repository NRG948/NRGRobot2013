package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.OperatorShooterCommand;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * A shooter utility without PID
 *
 * @author Patrick
 */
public class Shooter extends PIDSubsystem {
    
    public static final boolean USE_PID = false;

    private static final double P = 0.01;
    private static final double I = P / 2;
    private static final double D = 0.0;
    
    private double currentMotorSpeed = 0;
    
    private static final double pidOutputScaleValue = 0.1;
    private static final double pidDeactivationConstant = 0.5;

    public Shooter() {
        super("Shooter", P, I, D);
        setAbsoluteTolerance(0.2);
    }

    /**
     * set speed for manual control
     * @param speed
     */
    public void setSpeed(double speed) {
        if (USE_PID) {
            this.getPIDController().reset();
            this.setSetpoint(speed);
            this.enable();
        } else {
            this.disable();
            Robot.shooter.setSpeed(speed);
            SmartDashboard.putNumber("Shooting current speed", speed);
        }
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorShooterCommand());
    }

    public void stop() {
        this.disable();
        this.setShooterMotorSpeed(0);
    }

    protected double returnPIDInput() {
        return RobotMap.shooterQuadrature.getRate();
    }

    protected void usePIDOutput(double output) {
        PIDController pid = this.getPIDController();
        
        if (Math.abs(pid.getError()) > pidDeactivationConstant) {
            if (pid.getError() > 0) {
                setShooterMotorSpeed(1.0);
            } else {
                setShooterMotorSpeed(0);
            }

            pid.setPID(P, 0, 0);
        } else {
            pid.setPID(P, I, D);
            
            double speed = currentMotorSpeed + output * pidOutputScaleValue;

            speed = MathHelper.clamp(speed, 0, 1);

            setShooterMotorSpeed(speed);
        }
    }
    
    private void setShooterMotorSpeed(double speed) {
        RobotMap.shooterMotor.set(speed);
        currentMotorSpeed = speed;
    }
}
