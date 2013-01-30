/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Patrick
 */
public class RawShooter implements IShooter {

    public void setSpeed(double speed) {
        RobotMap.shootershootMotor.set(speed);
    }
    
}
