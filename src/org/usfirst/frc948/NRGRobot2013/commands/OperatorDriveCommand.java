package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.OI;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author hoileung
 */
public class OperatorDriveCommand extends Command {
    
    private OI oi;
    private double leftMotorSpeed;
    private double rightMotorSpeed;
    
    public OperatorDriveCommand(OI oi) {
        this.oi = oi;
    }
    
    protected void initialize() {
        this.requires(Robot.drive);
    }

    protected void execute() 
    {
        leftMotorSpeed = oi.getleftJoystick().getY();
        rightMotorSpeed = oi.getrightJoystick().getY();
        
        if (Math.abs(leftMotorSpeed) < 0.08) {
            leftMotorSpeed = 0;
        }
        
        if (Math.abs(rightMotorSpeed) < 0.08) {
            rightMotorSpeed = 0;
        }
        
        // TODO: averaging algorithm to compensate for sudden changes
        //
        // For instance, if the driver is going full speed forward on both 
        // joysticks and then suddenly pulls all the way back on both, there is 
        // the potential to damage the motors if we suddenly drive them the 
        // opposite way at full speed.
        
        Robot.drive.tankDrive(leftMotorSpeed, rightMotorSpeed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drive.tankDrive(0.0, 0.0);
    }

    protected void interrupted() {
        Robot.drive.tankDrive(0.0, 0.0);
    }
    
}
