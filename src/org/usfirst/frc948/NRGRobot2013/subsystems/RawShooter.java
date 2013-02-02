/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.utilities.ShooterControl;
/**
 * A shooter utility without PID 
 * @author Patrick
 */
public class RawShooter implements IShooter 
{
    
    private ShooterControl robotShooterControl = new ShooterControl(0, 0);;
    
    /**
     *
     * @param speed
     */
    public void setSpeed(double speed)
    {
        RobotMap.shootershootMotor.set(speed);
    }

    public ShooterControl getRobotShooterControl() {
        return robotShooterControl;
    }
    
}
