package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class ShooterSpeed extends PIDCommand {
    
    private static final double P = 0.01;
    private static final double I = 0.005;
    private static final double D = 0.0;
    
    private static final double pidOutputScaleValue = 0.1;
    private static final double pidDeactivationConstant = 200;
    
    private double pidOutput;
    
    public ShooterSpeed() {
        super(P, I, D);
        
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.shooter);
        
        this.getPIDController().setPercentTolerance(1.0);
        this.getPIDController().setInputRange(0, 3600);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double desiredRPM = OI.getRawShootSpeed() * 3400;
        
        if (Robot.oi.shooterUsePID()) {
            PIDController pid = this.getPIDController();

            SmartDashboard.putNumber("Shooter PID set", this.getPIDController().getSetpoint());
            SmartDashboard.putNumber("Shooter PID out", pidOutput);
            SmartDashboard.putNumber("Shooter PID err", this.getPIDController().getError());
            System.out.println("set: " + pid.getSetpoint() + " out: " + pidOutput + " err: " + pid.getError());
            LCD.println(true, 6,(int)this.getPIDController().getSetpoint() 
                    + " " + MathHelper.round(pidOutput, 4)
                    + " " + (int)this.getPIDController().getError());

            if (Math.abs(pid.getError()) > pidDeactivationConstant) {
                if (pid.getError() > 0) {
                    Robot.shooter.setShooterMotorPower(1.0);
                } else {
                    Robot.shooter.setShooterMotorPower(0.0);
                }

                pid.setPID(P, 0, 0);
            } else {
                pid.setPID(P, I, D);

                double newPower = MathHelper.clamp(Robot.shooter.getCurrentMotorPower() + pidOutput * pidOutputScaleValue, 0.0, 1.0);
                
                Robot.shooter.setShooterMotorPower(newPower);
            }
        } else {
            Robot.shooter.setShooterMotorPower(MathHelper.RPMtoPower(desiredRPM));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.shooter.setShooterMotorPower(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    protected double returnPIDInput() {
        return RobotMap.shooterQuadrature.getRPM();
    }

    protected void usePIDOutput(double output) {
        pidOutput = output;
    }
}
