package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.IOperatorInterface;

/**
 * Command to set the angle of the camera servo.
 *
 * @author Charles
 */
public class SetCameraTilt extends Command {

    private double servoVal;
    private boolean useSlider;
    private IOperatorInterface oi = Robot.oi;

    public SetCameraTilt() {
        requires(Robot.camera);
        useSlider = true;
    }

    public SetCameraTilt(double servoVal) {
        servoVal = this.servoVal;
        useSlider = false;
    }

    protected void initialize() {
    }

    protected void execute() {
        if (useSlider) {
            servoVal = oi.getCameraTilt();
        }
        RobotMap.cameraServo.set(servoVal);
    }

    protected boolean isFinished() {
        return !useSlider;
    }

    protected void end() {
    }
    
    protected void interrupted() {
        end();
    }
}
