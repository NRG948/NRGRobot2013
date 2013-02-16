package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    public static final double pidDeactivationConstant = 0.5;
    private static final double speedUpConstant = 0.05;
    private static double speed;
    private static boolean speedUpActivated;
    private static int a;

    public Shooter() {
        super("Shooter", P, I, D);
        setAbsoluteTolerance(0.2);
        if (USE_PID) {
            this.enable();
        } else {
            this.disable();
        }
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
            RobotMap.shooterMotor.set(speed);
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
        if (USE_PID) {
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
                if(speedUpActivated){
//                    shooterSpeedUp();
 //                   a ++;
 //                   if(a >5){
  //                      speedUpActivated = false;
  //                  }
                }

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
    
//    public void shooterSpeedUp(){
//        speed *= (1+speedUpConstant);
 //       speedUpActivated = false;
 //       a = 0; 
//    }
    
    public boolean isAtSpeed() {
        return this.onTarget();
    }
    
}
