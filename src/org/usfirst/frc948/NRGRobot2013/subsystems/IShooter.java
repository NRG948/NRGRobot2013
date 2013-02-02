/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.utilities.ShooterControl;

/**
 *
 * @author Patrick
 */
public interface IShooter {
    
    public void setSpeed(double speed);
    
    public ShooterControl getRobotShooterControl();
}
