package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.Drive;

/**
 * Command to reset all sensors/encoders on the robot.
 *
 * @author irving
 */
public class ResetSensorsCommand extends Command {

    public ResetSensorsCommand() {
        requires(Robot.drive);
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        Robot.drive.resetGyro();
        Robot.drive.setDesiredHeading(0);
        Drive.resetLeftEncoder();
        Drive.resetRightEncoder();
        RobotMap.shooterQuadrature.reset();
        Robot.positionTracker.init();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
