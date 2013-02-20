package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Charles
 */
public class SetCameraTilt extends Command {

    private double servoVal;
    private boolean useSlider;
    private OI oi = Robot.oi;

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
