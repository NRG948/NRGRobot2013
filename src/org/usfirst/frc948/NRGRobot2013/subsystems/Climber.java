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
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;

/**
 * @Author Patrick, Jared, Charles
 */
public class Climber extends Subsystem {
    
    public static final double DEFAULT_POWER = 0.3;

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * pressing a button turn clockwise in a certain amount of time
     */
    //positive param = climb up, negative means climb down
    public void setClimberMotorPower(double climbValue) {
        if (RobotMap.tiltPiston.get() == Relay.Value.kOff) {
//            LCD.println(true, 6, "!--ENGAGE CLIMBER--!");
        } else {
            RobotMap.climberMotor1.set(-climbValue);
            RobotMap.climberMotor2.set(-climbValue);
            LCD.clearLine(6);
        }
    }

    public void stop() {
        RobotMap.climberMotor1.set(0);
        RobotMap.climberMotor2.set(0);
    }

    public void activateTilt() {
        RobotMap.tiltPiston.set(Relay.Value.kOn);
    }

    public void disengage() {
        RobotMap.tiltPiston.set(Relay.Value.kOff);
    }
}
