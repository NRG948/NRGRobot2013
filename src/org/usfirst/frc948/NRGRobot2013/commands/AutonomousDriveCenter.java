/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.Robot;
/**
 *
 * @author Sean
 */
public class AutonomousDriveCenter extends CommandGroup{
    private static double turningAngle = 15;
    private static double returnDistance = 5;
    //to do: find accurate turning angles and return distances for center location. 
    private static TurnCommand Turn= new TurnCommand(0.5, turningAngle);
    private static DriveStraightDistance Drive = new DriveStraightDistance(0.5, returnDistance);
    public AutonomousDriveCenter(){
        requires(Robot.drive);
        addSequential (Turn);
        addSequential (Drive);
    }
    
}
