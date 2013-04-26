package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author irving
 */
public class Extender extends Subsystem {
    
    // 1 - left
    
    private static final double SERVO_LEFT_STORE = 0.1;
    private static final double SERVO_RIGHT_STORE = 0.0;
    
    private static final double SERVO_LEFT_EXTEND = 0.7;
    private static final double SERVO_RIGHT_EXTEND = 0.6;
    
    private boolean isDown = false;
    
    protected void initDefaultCommand() {}
    
    public void toggle() {
        if (isDown) {
            RobotMap.extenderServoLeft.set(SERVO_LEFT_STORE);
            RobotMap.extenderServoRight.set(SERVO_RIGHT_STORE);
            isDown = false;
        } else {
            RobotMap.extenderServoLeft.set(SERVO_LEFT_EXTEND);
            RobotMap.extenderServoRight.set(SERVO_RIGHT_EXTEND);
            isDown = true;
        }
    }
    
    public boolean isDown() {
        return isDown;
    }
    
}
