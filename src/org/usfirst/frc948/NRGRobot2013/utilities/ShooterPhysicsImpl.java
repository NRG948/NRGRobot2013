/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.utilities;

import org.usfirst.frc948.NRGRobot2013.utilities.ShooterPhysics;
import org.usfirst.frc948.NRGRobot2013.utilities.ShooterControl;

/**
 * Implements the ShooterPhysics interface to set the ShooterControl parameters
 * 
 * @author Patrick Lin
 */
public class ShooterPhysicsImpl implements ShooterPhysics {
    
    /**
     * Calculates the speed and angle of the disc as it leaves the shooter
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return 
     */
        final double gravity = -9.80;
        final double densityOfAir = 1.23;
        final double massOfFrisbee = 0.18;
        final double areaOfFrisbee = 95.03;
        final double viscosityOfAir = 0.0000173;
        final double pressureOfAir = 101325;
    public ShooterControl calculate(double distance, double platformHeight, double targetHeight) {
        double angle = 0;
        double speed = 0;
        return new ShooterControl(angle, speed);
    }
    
}
