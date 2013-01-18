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
    
    // Physical parameters for the numerical calculations (in MKS units)
    private static double x; //The x position of the frisbee.
    private static double y; //The y position of the frisbee.
    private static double vx; //The x velocity of the frisbee.
    private static double vy; //The y velocity of the frisbee.
    private static final double g = 9.81d; //The acceleration of gravity (m/s^2).
    private static final double m = 0.175d; //The mass of a standard frisbee in kilograms.
    private static final double RHO = 1.23d; //The density of air in kg/m^3.
    private static final double AREA = 0.0568d; //The area of a standard frisbee.
    private static final double CL0 = 0.3331d; // 0.1; //The lift coefficient at alpha = 0.
    private static final double CLA = 1.9124d; //1.4; //The lift coefficient dependent on alpha.
    private static final double CD0 = 0.1769d; // 0.08; //The drag coefficent at alpha = 0.
    private static final double CDA = 0.685d; //2.72; //The drag coefficient dependent on alpha.
    private static final double ALPHA0 = 0d; // The initial angle of attack
    
    /**
     * Calculates the speed and angle of the disc as it leaves the shooter
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return 
     */
        
    public ShooterControl calculate(double distance, double platformHeight, double targetHeight) {
        double angle = 0;
        double speed = 0;
        
        // Initial height of frisbee (at launch)
        double yP = platformHeight; // meters\
        
        double yT = targetHeight;
        
        double alpha = 0d; // angle of attack
        
        double dt = 0.001d; // seconds (simulation timestep)
        
        return new ShooterControl(angle, speed);
    }
    
}
