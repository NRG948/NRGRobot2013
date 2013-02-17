package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.OperatorShooterCommand;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * A shooter utility without PID
 *
 * @author Patrick
 */
public class Shooter extends PIDSubsystem {

    public static final boolean DEFAULT_USE_PID = false;
    private boolean usePID;
    
    private static final double P = 0.01;
    private static final double I = P / 2;
    private static final double D = 0.0;
    private static final double pidOutputScaleValue = 0.1;
    private static final double pidDeactivationConstant = 0.5;
    
    public static final double DEFAULT_OVER_REV = 1.10;
    public static final long SHOOT_DELAY_TIME = 3000;
    
    private double currentMotorSpeed = 0;
    private double speed;
    private double overRevFactor = 1.0;

    public Shooter() {
        super("Shooter", P, I, D);
        setAbsoluteTolerance(0.2);
        usePID = Preferences.getInstance().getBoolean(PreferenceKeys.SHOOTER_USE_PID, DEFAULT_USE_PID);
        if (usePID) {
            this.enable();
        } else {
            this.disable();
        }
    }

    /**
     * set speed for manual control
     *
     * @param speed
     */
    public void setSpeed(double speed) {
        if (usePID) {
            this.getPIDController().reset();
            this.setSetpoint(speed);
            this.enable();
        } else {
            RobotMap.shooterMotor.set(MathHelper.clamp(speed * overRevFactor, -1.0, 1.0));
        }
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorShooterCommand());
    }

    protected double returnPIDInput() {
        double rate = RobotMap.shooterQuadrature.getRate();
        SmartDashboard.putNumber("shooter rate", rate);
        return rate;
    }

    protected void usePIDOutput(double output) {
        if (usePID) {
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

                speed = currentMotorSpeed + output * pidOutputScaleValue;

                speed = MathHelper.clamp(speed, 0, 1);

                setShooterMotorSpeed(speed);
            }
        }
    }

    private void setShooterMotorSpeed(double speed) {
        SmartDashboard.putNumber("shooter set", speed);
        RobotMap.shooterMotor.set(speed);
        currentMotorSpeed = speed;
    }

    public void stop() {
        this.disable();
        RobotMap.shooterMotor.set(0);
    }

    public boolean isAtSpeed() {
        if (usePID) {
            return this.onTarget();
        } else {
            return (System.currentTimeMillis() - Robot.discMagazine.getTimeOfLastShot()) > SHOOT_DELAY_TIME;
        }

    }

    public void setOverRev(double d) {
        overRevFactor = d;
    }
}
