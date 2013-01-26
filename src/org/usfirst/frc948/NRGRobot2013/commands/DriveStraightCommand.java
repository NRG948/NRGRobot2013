/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import java.lang.Math;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
/**
 *
 * @author Patrick Lin & Sean Yu
 */
public class DriveStraightCommand extends Command {
    
    private PIDController leftPID;
    private PIDController rightPID;
    private final double RADIUS = 3.0;
    private final double CIRCUMFERENCE = 2d * Math.PI * RADIUS;
    private RobotMap robotMap;
    private double distance;
    
    private double getDistanceTraveled(double angle) {
        
        return CIRCUMFERENCE * angle / 360d;
    }    
    
    public DriveStraightCommand(RobotMap robotMap, String name, double timeout) {        
        super(name, timeout);
        this.robotMap = robotMap;
    }
    
    public void setDistance(double distance) {
        
        this.distance = distance;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
