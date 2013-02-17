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
    
    //TODO: Determine power levels to shoot from positions 0, 1, 2, 3, 4, and 5
    private double[] powers = {0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
    
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
        requires(Robot.discMagazine);
        //TODO: set the power and angle for each position
        
       
        addSequential(new AutonomousPIDShooterCommand(powers[position]));
        for(int i = 0; i < 4; i++) {
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(100));
        }
        
    }
}
