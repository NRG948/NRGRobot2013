/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.utilities;

import edu.wpi.first.wpilibj.*;

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
    public void update() {
        if ( initialized == false)
        {
            initialized = true;
            init();
        }
        double newLeftQuadDistance = driveLeftQuad.getDistance() - lastLeftQuadDistance;
        double newRightQuadDistance = driveRightQuad.getDistance() - lastRightQuadDistance;
        double average = (newLeftQuadDistance + newRightQuadDistance) / 2;
        double degrees = MathHelper.HeadingToDegrees(driveGyro.getAngle());
        
        y += Math.sin(Math.toRadians(degrees)) * average;
        x += Math.cos(Math.toRadians(degrees)) * average;
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
