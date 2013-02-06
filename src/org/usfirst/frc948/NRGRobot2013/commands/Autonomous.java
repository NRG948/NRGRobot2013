/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.Robot;

/**
 *
 * @author holeung
 */
public class Autonomous extends CommandGroup {
    //autonomous starting positions
    private int position;
    //angle for the shooter durving autonomous
    //based on position
    private double angle;
    //power for the shooter during autonomous
    //based on position
    private double power;
    
    public Autonomous() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        requires(Robot.aimSystem);
        requires(Robot.discMagazine);
        //TODO: set the power and angle for each position
        switch(position) {
            case 0:
                power = 0.5;
                angle = 45;
                break;
            case 1:
                power = 0.5;
                angle = 45;
                break;
            case 2:
                power = 0.5;
                angle = 45;
                break;
            case 3:
                power = 0.5;
                angle = 45;
                break;
            case 4:
                power = 0.5;
                angle = 45;
                break;
            case 5:
                power = 0.5;
                angle = 45;
                break;    
        }
        addSequential(new AutonomousPIDShooterCommand(angle, power));
        for(int i = 0; i < 4; i++) {
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(100));
        }
        
    }
}
