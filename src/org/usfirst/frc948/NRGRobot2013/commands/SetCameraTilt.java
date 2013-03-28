package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 * Command to set the angle of the camera servo.
 *
 * @author Charles
 */
public class SetCameraTilt extends Command {

    private final boolean relative;
    private final double value;
    
    // set absolute position of servo
    public SetCameraTilt(double servoVal) {
        relative = false;
        this.value = servoVal;
    }
    
    // adjust position of servo (relative)
    public SetCameraTilt(int direction, double speed) {
        relative = true;
        this.value = direction * speed;
    }

    protected void initialize() {
    }

    protected void execute() {
        if (relative) {
            RobotMap.cameraServo.set(RobotMap.cameraServo.get() + value);
        } else {
            RobotMap.cameraServo.set(value);
        }
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }
    
    protected void interrupted() {
        end();
    }
}
