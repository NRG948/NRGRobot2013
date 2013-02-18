/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 *
 * @author Charles
 */
public class AutonomousShoot3Frisbee extends CommandGroup{
    
    private static final double SPEED = .85;
    public AutonomousShoot3Frisbee()
    {
        addParallel(new SetMotorPower(RobotMap.shooterMotor,SPEED));
        for ( int inx = 0; inx < 3 ; inx++ )
        {
            addParallel(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            
            if ( isFinished() )
            {
                end();
            }
        }
    }
    
}
