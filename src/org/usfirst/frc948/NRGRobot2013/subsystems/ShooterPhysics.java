/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;

/**
 * Defines the interface for calculating the ShooterControl parameters.
 * 
 * @author Patrick Lin
 */
public interface ShooterPhysics {
    
    /**
     * 
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return 
     */
    public ShooterControl calculate(double distance, double platformHeight, double targetHeight); 
        
}
