/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.utilities;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author holeung
 */
public class PositionTracker {

    private Gyro driveGyro;
    private Encoder driveLeftQuad, driveRightQuad;
    private double x, y;
    private double lastLeftQuadDistance, lastRightQuadDistance;
    private boolean initialized = false ;
    
    public PositionTracker(Gyro driveGyro, Encoder driveLeftQuad, Encoder driveRightQuad) {
        this.driveGyro = driveGyro;
        this.driveLeftQuad = driveLeftQuad;
        this.driveRightQuad = driveRightQuad;
    }
    public void init()
    {
        x = 0;
        y = 0;
        
        driveLeftQuad.reset();
        driveRightQuad.reset();
        lastLeftQuadDistance = 0;
        lastRightQuadDistance = 0;
    }
    
    // Update the robot's current x,y field position based the sum of polar vectors created by reading the gyro heading and the drive encoders.
    // The x,y coordinates tracked are for the rotational centerpoint of the robot.
    // All on-field coordinates are always positive, such that (0,0) is the field corner to the driver's left.
    // The center of the 3-pt goal is located at (13.5, 54.0).
    public void update() {
        if (initialized == false) {
            init();
            initialized = true;
        }
        double newLeftQuadDistance = driveLeftQuad.getDistance() - lastLeftQuadDistance;
        double newRightQuadDistance = driveRightQuad.getDistance() - lastRightQuadDistance;
        double average = (newLeftQuadDistance + newRightQuadDistance) / 2;  // distance the robot centerpoint moved
        double radians = Math.toRadians(MathHelper.HeadingToDegrees(driveGyro.getAngle()));
        
        x += Math.cos(radians) * average;
        y += Math.sin(radians) * average;
        SmartDashboard.putNumber("X: ", MathHelper.round(x,2));
        SmartDashboard.putNumber("Y: ", MathHelper.round(y,2));
        lastLeftQuadDistance = newLeftQuadDistance;
        lastRightQuadDistance = newRightQuadDistance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = 0;
        y = 0;
        lastLeftQuadDistance = driveLeftQuad.getDistance();
        lastRightQuadDistance = driveRightQuad.getDistance();
    }
    
    public void setNewPosition(double newX, double newY) {
        x = newX;
        y = newY;
    }
    
}
