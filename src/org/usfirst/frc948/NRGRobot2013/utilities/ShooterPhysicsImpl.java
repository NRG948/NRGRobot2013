package org.usfirst.frc948.NRGRobot2013.utilities;

import java.util.*;
import java.lang.Math;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 * Implements the ShooterPhysics interface to set the ShooterControl parameters
 *
 * @author Patrick Lin & Char Chen
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
    private static final double MIDDLE_GOAL_HEIGHT = 7.38541667d; // in feet; area = 54 in. x 21 in. 
    private static final double HIGH_GOAL_HEIGHT = 8.666667d; //in feet; area = 54 in. x 12 in. 
    private static final double ALLIANCE_LOW_GOAL_HEIGHT = 1.58333d; //in feet; area = 29 in. x 24 in.
    private static final double MAX_FRISBEE_SPEED = 30d;
    /**
     * Calculates the speed and angle of the disc as it leaves the shooter
     *
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target8
     * @return
     */
    //returns an array of possible angles and pseeds that can reach target.   can we just return the best possiblity 
    // via teh program
    public ShooterControl calculate(double distance, double platformHeight, int goalType) 
    {
        double targetHeight = 0;
        if ( goalType == 1 )
        {
            targetHeight = ShooterPhysicsImpl.ALLIANCE_LOW_GOAL_HEIGHT;
        }
        else if ( goalType == 2 )
        {
            targetHeight = ShooterPhysicsImpl.MIDDLE_GOAL_HEIGHT;
        }
        else 
        {
            targetHeight = ShooterPhysicsImpl.HIGH_GOAL_HEIGHT;
        }
        double angle = 0d;
        double speed = 0d;
        ShooterControl[] angleArr = new ShooterControl[45];  //angle as a function of speed ?
        ShooterControl idealShooterControl = new ShooterControl(0, 0);
        
        // Initial height of frisbee (at launch)
        double yP = platformHeight; // meters
        
        // Height of the target
        double yT = targetHeight;
        
        double alpha = 0d; // angle to shoot
        
        double dt = 0.001d; // seconds (simulation timestep)
        int i = 0;
        while (i < 45) 
        {
            angle = i + 1;
            //Calculate speed nessesary for angle i + 1 to hit target. 
            angleArr[i] = new ShooterControl(angle, speed);
            if (speed > MAX_FRISBEE_SPEED)
            {
                angleArr[i] = null;
            }
            i++;
        }
        double b = 99;
        int c = 0;
        while (c < 45)
        {
            double currentAngle = Robot.shooter.getRobotShooterControl().getAngle();
            double nessesaryAngle = angleArr[i].getAngle();
            double d = currentAngle - nessesaryAngle;
            if (Math.abs(d) < b) 
            {
                b = Math.abs(d);
                idealShooterControl = angleArr[i];
            }
        }
        
        return idealShooterControl;
    }
}
