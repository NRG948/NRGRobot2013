/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;

/**
 * Implements the ShooterPhysics interface to set the ShooterControl parameters
 * 
 * @author Patrick Lin
 */
public class ShooterPhysicsImpl implements ShooterPhysics {
    
    /**
     * 
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return 
     */
    public ShooterControl calculate(double distance, double platformHeight, double targetHeight) {
        double angle = 0;
        double speed = 0;
        
        
        
        return new ShooterControl(angle, speed);
    }
    
}
