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

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;


/**
 * @Author Patrick, Jared, Charles
 */
public class Climber extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Relay tiltPiston = RobotMap.tiltPiston;
    
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * using joystick turn clockwise in a certain amount of time 
     */
    public void turnClockwise(double climbValue) {
        RobotMap.climberMotor.set(climbValue);
        
    }
    
    public void turnCounterClockwise(double climbValue) {
        RobotMap.climberMotor.set(-climbValue);
        
    }
    
    public void stop() {
        RobotMap.climberMotor.set(0);
    }
    
    /**
     * robot leans forward by activating the piston
     * @throws InterruptedException 
     */
    public void activateTilt() throws InterruptedException {
        tiltPiston.set(Relay.Value.kForward);
    }
}

