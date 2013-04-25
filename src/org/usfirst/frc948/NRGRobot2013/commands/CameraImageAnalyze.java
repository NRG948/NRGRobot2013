package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author irving
 */
public class CameraImageAnalyze extends Command {

    protected void initialize() {
        
    }

    protected void execute() {
        Robot.camera.analyzeImage();
        Robot.camera.analyzeReports();
        Robot.camera.writeToVisionTable();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    
}
