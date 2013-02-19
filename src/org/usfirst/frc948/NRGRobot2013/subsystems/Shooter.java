package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.OperatorShooterSpeed;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * A shooter utility without PID
 *
 * @author Patrick
 */
public class Shooter extends PIDSubsystem {
    
    public static final boolean DEFAULT_USE_PID = false;

    private static final double P = 0.01;
    private static final double I = P / 2;
    private static final double D = 0.0;
    private static final double pidOutputScaleValue = 0.1;
    private static final double pidDeactivationConstant = 200;
    
    public static final double DEFAULT_OVER_REV = 1.10;
    public static final long SHOOT_DELAY_TIME = 3000;
    
    private double currentMotorPower = 0;
    private double speed;
    private double overRevFactor = 1.0;
    
    public Shooter() {
        super("Shooter", P, I, D);
        setPercentTolerance(1.0);
//        usePID = Preferences.getInstance().getBoolean(PreferenceKeys.SHOOTER_USE_PID, DEFAULT_USE_PID);
//        if (usePID) {
//            this.enable();
//        } else {
//            this.disable();
//        }
        this.enable();
    }
        
    public void setRawPower(double power) {
        power = MathHelper.clamp(power * overRevFactor, 0, 1.0);
        RobotMap.shooterMotor.set(-power);
        currentMotorPower = power;
    }
    
    public void setDesiredRPM(double rpm) {
//            if (Math.abs(this.getSetpoint() - speed) / this.getSetpoint() > 0.5) {
//                this.getPIDController().reset(); //note: reset writes zero to the PIDoutput
//            }
            SmartDashboard.putNumber("shooter setRpm", rpm);
            this.enable();
            this.setSetpoint(rpm);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorShooterSpeed());
        SmartDashboard.putData("Shooter", this);
        SmartDashboard.putData("ShooterPID", this.getPIDController());
    }

    protected double returnPIDInput() {
        return RobotMap.shooterQuadrature.getRPM();
    }

    protected void usePIDOutput(double output) {
        System.out.print("entering usePIDoutput: ");
        if (Robot.oi.shooterUsePID()) {
            PIDController pid = this.getPIDController();
            
            SmartDashboard.putNumber("Shooter PID set", this.getPIDController().getSetpoint());
            SmartDashboard.putNumber("Shooter PID out", output);
            SmartDashboard.putNumber("Shooter PID err", this.getPIDController().getError());
            System.out.println("set: " + pid.getSetpoint() + " out: " + output + " err: " + pid.getError());
            LCD.println(true, 6, "PIDset:"+ (int)this.getPIDController().getSetpoint() 
                    + " PIDout:" + (int)output
                    + " PIDerr:" + (int)this.getPIDController().getError());
            
            if (Math.abs(pid.getError()) > pidDeactivationConstant) {
                if (pid.getError() > 0) {
                    setShooterMotorSpeed(1.0);
                } else {
                    setShooterMotorSpeed(0);
                }

                pid.setPID(P, 0, 0);
            } else {
                pid.setPID(P, I, D);
                
                speed = currentMotorPower + output * pidOutputScaleValue;

                speed = MathHelper.clamp(speed, 0, 1);

                setShooterMotorSpeed(speed);
            }
        }
    }

    private void setShooterMotorSpeed(double speed) {
        
        RobotMap.shooterMotor.set(-speed);
        currentMotorPower = speed;
    }

    public void stop() {
        this.disable();
        setShooterMotorSpeed(0);
    }

    public boolean isAtSpeed() {
        if (Robot.oi.shooterUsePID()) {
            return this.onTarget();
        } else {
            return (System.currentTimeMillis() - Robot.discMagazine.getTimeOfLastShot()) > SHOOT_DELAY_TIME;
        }

    }

    public void setOverRev(double d) {
        overRevFactor = d;
    }
    
    public void reset() {
        this.getPIDController().reset();
        this.getPIDController().enable();
    }
}
