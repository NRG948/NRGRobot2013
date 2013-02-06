/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author holeung
 */
public class AutonomousPIDShooterCommand extends Command{

    private double angle;
    private double power;
    public AutonomousPIDShooterCommand(double angle, double power) {
        //requires(Robot.PIDShooter);
        this.angle = angle;
        this.power = power; 
    }
    protected void initialize() {
        
    }

    protected void execute() {
        Robot.PIDShooter.getRobotShooterControl().changeAngle(angle);
        Robot.PIDShooter.getRobotShooterControl().changeSpeed(power);
    }

    protected boolean isFinished() {
        if(ReleaseFrisbeeCommand.count>=4) {
            return true;
        }
        return false;
    }

    protected void end() {
        Robot.PIDShooter.stop();
    }

    protected void interrupted() {
        end();
    }
    
}
