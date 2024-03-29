/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc948.NRGRobot2013.commands.Autonomous;

/**
 *
 * @author ALISHA
 */
public interface IOperatorInterface {

    Autonomous.StartingPosition getAutonomousStartingPosition();

    Autonomous.TargetPosition getAutonomousTargetPosition();

    double getCameraTilt();

    boolean getDriveSlow();

    boolean getDriveStraight();

    double getRawShootSpeed();

    double getShootTrimPower();

    double getShootTrimRPM();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    Joystick getleftJoystick();

    Joystick getrightJoystick();

    void update();
    
    boolean isFullAutonomous();
    
    boolean autoShootEnabled();
    
}
