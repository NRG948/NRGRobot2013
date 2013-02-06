package org.usfirst.frc948.NRGRobot2013.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.ShooterControl;
/**
 * A shooter utility without PID 
 * @author Patrick
 */
public class RawShooter extends Subsystem implements IShooter 
{
    
    private ShooterControl robotShooterControl;
    public RawShooter() {
        robotShooterControl = new ShooterControl(0,0);
    }
    /**
     *
     * @param speed
     */
    public void setSpeed(double speed)
    {
        robotShooterControl.changeSpeed(speed);
    }

    public ShooterControl getRobotShooterControl() {
        return robotShooterControl;
    }

    protected void initDefaultCommand() {
    }

    public void stop() {
        robotShooterControl = new ShooterControl(0,0);
    }

  

    
}
