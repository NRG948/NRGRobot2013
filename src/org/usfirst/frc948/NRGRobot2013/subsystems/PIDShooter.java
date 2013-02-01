// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.RobotMap;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc948.NRGRobot2013.utilities.ShooterControl;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.lang.Math;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
/**
 *
 */
public class PIDShooter extends PIDSubsystem implements IShooter {
    
    private Encoder shooterQuadrature = RobotMap.shootershootQuadrature;
    private double shooterSpeed = 0; //Angular velocity of the wheel
    private static final double P = 0.01; 
    private static final double I = P/2;
    private static final double D = 0.0;
    private static final double pidOutputScaleValue = 0.1;
    private static final double pidActivationConstant = 0.1;
    private static final double pidDeactivationConstant = 0.5;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController shootMotor = RobotMap.shootershootMotor;
    Encoder shootQuadrature = RobotMap.shootershootQuadrature;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public ShooterControl robotShooterControl;
    
    // Initialize your subsystem here
    public PIDShooter() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("Shooter", P, I, D);
        robotShooterControl = new ShooterControl(0, 0);
        setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        LiveWindow.addActuator("Shooter", "PIDSubsystem Controller", getPIDController());
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    //sets the desireable speed of the wheel
    public void setSpeed(double speed)
    {
        shooterSpeed = speed;
        if(Math.abs(shooterSpeed - shooterQuadrature.getRate())> pidDeactivationConstant){
            disable();
            if(shooterSpeed - shooterQuadrature.getRate() >0){
                shootMotor.set(1.0);
            }
            else {
                shootMotor.set(0);
            }
            if(Math.abs(shooterSpeed - shooterQuadrature.getRate()) < pidActivationConstant){
                enable();
                this.setSetpoint(shooterSpeed);
            }  
        }
        else if(Math.abs(shooterSpeed - shooterQuadrature.getRate()) <= pidDeactivationConstant){
            this.setSetpoint(speed);
        }
    }

    public void initDefaultCommand() 
    {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput()
    {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SOURCE
        return shooterQuadrature.getRate();
    }

    protected void usePIDOutput(double d)
    {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        shooterSpeed += d*pidOutputScaleValue;
        if (shooterSpeed > 1 || shooterSpeed < -1)
        {
            shooterSpeed = MathHelper.clamp(shooterSpeed, -1, 1);
        }
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
        
        RobotMap.shootershootMotor.set(shooterSpeed);
    }
    
    
}
