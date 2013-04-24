package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author irving
 */
public class Extender extends Subsystem {
    
    private static final double SERVO1_STORE = 1.0;
    private static final double SERVO2_STORE = 0.0;
    
    private static final double SERVO1_EXTEND = 0.35;
    private static final double SERVO2_EXTEND = 0.65;
    
    private boolean isDown = false;
    
    protected void initDefaultCommand() {}
    
    public void toggle() {
        if (isDown) {
            RobotMap.extenderServo1.set(SERVO1_STORE);
            RobotMap.extenderServo2.set(SERVO2_STORE);
            isDown = false;
        } else {
            RobotMap.extenderServo1.set(SERVO1_EXTEND);
            RobotMap.extenderServo2.set(SERVO2_EXTEND);
            isDown = true;
        }
    }
    
}
