package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

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
        Debug.println("[ResetSensorsCommand]");
        RobotMap.drivegyro.reset();
        Robot.drive.setDesiredHeading(0);
        Robot.drive.resetLeftEncoder();
        Robot.drive.resetRightEncoder();
        RobotMap.shooterQuadrature.reset();
        Robot.positionTracker.init();
        Robot.positionTracker.setPosition(14.5, 37.00);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
