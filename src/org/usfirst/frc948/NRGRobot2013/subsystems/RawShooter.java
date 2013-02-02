package org.usfirst.frc948.NRGRobot2013.subsystems;

import org.usfirst.frc948.NRGRobot2013.RobotMap;
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
        RobotMap.shooterMotor.set(speed);
    }

    public ShooterControl getRobotShooterControl() {
        return robotShooterControl;
    }

    public void stop() {
    }
    
}
