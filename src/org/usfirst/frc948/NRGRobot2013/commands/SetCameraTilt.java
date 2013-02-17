/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Charles
 */
public class SetCameraTilt extends Command{
    private double servoVal;
    private boolean useSlider;
    private OI oi = Robot.oi;
    public SetCameraTilt()
    {
        requires(Robot.camera);
        useSlider = true;
        System.out.println("Camera Tilt has been constructed");
    }
    public SetCameraTilt(double servoVal)
    {
        servoVal = this.servoVal;
        useSlider = false;
    }
    protected void initialize() {
        System.out.println("Camera Tilt is initialized");
    }

    protected void execute() {
        
        if ( useSlider )
        {
            servoVal = oi.getCameraTilt();
        }
        RobotMap.cameraServo.set(servoVal);
        System.out.println("Camera Tilt has been executed");
    }
    
    protected boolean isFinished() {
        return !useSlider;
    }

    protected void end() {
        System.out.println("Camera Tilt has ended");
    }

    protected void interrupted() {
        System.out.println("Camera Tilt has been interrupted");
        end();
    }
    
}
