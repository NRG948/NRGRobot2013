package org.usfirst.frc948.NRGRobot2013.utilities;
/**
 * Contains the angle and speed at which the platform fires the disc.
 * 
 * @author Patrick Lin
 */
public class ShooterControl {
 
    private double angle; //The angle between the shooter platform and the horizontal
    private double speed; //The velocity of the disc leaving the shooter
    
    public ShooterControl(double angle, double speed) {
        this.angle = angle;
        this.speed = speed;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getSpeed() {
        return speed;
    }
}
