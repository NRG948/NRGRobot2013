package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Kevin & Patrick
 */
public class OperatorShooterCommand extends PIDCommand {
    // http://lcec.us/javadoc/edu/wpi/first/wpilibj/command/Command.html

    private double shooterSpeed = 0; //Angular velocity of the wheel
    private Encoder shooterQuadrature = RobotMap.shootershootQuadrature;
    private static final double P = 0.01; 
    private static final double I = P/2;
    private static final double D = 0.0;
    
    public OperatorShooterCommand() {
        super(P, I, D);
        requires(Robot.shooter);
    }
    
    //sets the desireable speed of the wheel
    public void setSpeed(double speed) {
        shooterSpeed = speed;
        this.setSetpoint(speed);
    }
    
    protected void initialize() {
                
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return false; //TODO get result from switch
    }

    protected void end() {
        RobotMap.shootershootMotor.set(0.0);
    }

    protected void interrupted() {
        RobotMap.shootershootMotor.set(0.0);
    }

    protected double returnPIDInput() {
        return shooterQuadrature.getRate();
    }

    protected void usePIDOutput(double d) {
        
        if (d > 0) {
            shooterSpeed *= (1 + d);
        } else if (d < 0) {
            shooterSpeed *= (1 - d);
        }
        
        RobotMap.shootershootMotor.set(shooterSpeed);
    }
}