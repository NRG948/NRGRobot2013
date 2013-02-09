/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author holeung
 */
public class OperatorAimCommand extends Command {

    private boolean goingUp;
    
    public OperatorAimCommand(boolean goingUp) {
        this.goingUp = goingUp;
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        double power = goingUp ? Preferences.getInstance().getDouble("AngleMotorSpeedUp", .2): 
                -Preferences.getInstance().getDouble("AngleMotorSpeedDown", .2);
        RobotMap.shooterAngleMotor.set(power);
        
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        RobotMap.shooterAngleMotor.set(0);
    }

    protected void interrupted() {
        end();
    }
    
}
