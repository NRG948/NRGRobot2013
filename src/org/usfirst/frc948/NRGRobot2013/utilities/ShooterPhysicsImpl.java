package org.usfirst.frc948.NRGRobot2013.utilities;

import org.usfirst.frc948.NRGRobot2013.subsystems.PIDShooter;
import java.util.*;
import java.lang.Math;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Implements the ShooterPhysics interface to set the ShooterControl parameters
 *
 * @author Patrick Lin
 */
public class ShooterPhysicsImpl implements ShooterPhysics 
{

    // Physical parameters for the numerical calculations (in MKS units)
    private static double x; //The x position of the frisbee.
    private static double y; //The y position of the frisbee.
    private static double vx; //The x velocity of the frisbee.
    private static double vy; //The y velocity of the frisbee.
    private static final double G = 9.81d; //The acceleration of gravity (M_Frisbee/s^2).
    private static final double M_Frisbee = 0.175d; //The mass of a standard frisbee in kilograms.
    private static final double RHO = 1.23d; //The density of air in kg/m^3.
    private static final double AREA = 0.0568d; //The area of a standard frisbee.
    private static final double CL0 = 0.3331d; // 0.1; //The lift coefficient at alpha = 0.
    private static final double CLA = 1.9124d; //1.4; //The lift coefficient dependent on alpha.
    private static final double CD0 = 0.1769d; // 0.08; //The drag coefficent at alpha = 0.
    private static final double CDA = 0.685d; //2.72; //The drag coefficient dependent on alpha.
    private static final double ALPHA0 = 0d; // The initial angle of attack

    /**
     * Calculates the speed and angle of the disc as it leaves the shooter
     *
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return
     */
    //returns an array of possible angles and pseeds that can reach target. 
    public ShooterControl calculate(double distance, double platformHeight, double targetHeight) 
    {
        double angle = 0d;
        double speed = 0d;
        ShooterControl[] a1 = new ShooterControl[45];
        ShooterControl idealShooterControl = new ShooterControl(0, 0);

        // Initial height of frisbee (at launch)
        double yP = platformHeight; // meters
        
        // Height of the target
        double yT = targetHeight;
        
        double alpha = 0d; // angle of attack

        double dt = 0.001d; // seconds (simulation timestep)
        int i = 0;
        while (i < 45) 
        {
            angle = i + 1;
            //Calculate speed nessesary for angle i + 1 to hit target. 
            a1[i] = new ShooterControl(angle, speed);
            i++;
            if (speed > 30)
            {
                a1[i] = null;
            }
        }
        double b = 99;
        int c = 0;
        while (c < 45)
        {

            double currentAngle = Robot.shooter.robotShooterControl.getAngle();
            double nessesaryAngle = a1[i].getAngle();
            double d = currentAngle - nessesaryAngle;
            if (Math.abs(d) < b) 
            {
                b = Math.abs(d);
                idealShooterControl = a1[i];
            }
        }
        
        return idealShooterControl;
    }
}
