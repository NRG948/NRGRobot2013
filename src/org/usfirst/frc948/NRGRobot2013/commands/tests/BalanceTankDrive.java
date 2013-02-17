package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.commands.Delay;
import org.usfirst.frc948.NRGRobot2013.commands.RawSeparateTankDrive;

/**
 *
 * @author irving
 */
public class BalanceTankDrive extends CommandGroup {
    
    // roughly straight: 0.5, 0.57
    
    public BalanceTankDrive() {
        addSequential(new RawSeparateTankDrive(0.7, 0.7, 1500));
        addSequential(new Delay(1000));
        addSequential(new RawSeparateTankDrive(0.7, 0.71, 1500));
        addSequential(new Delay(1000));
        addSequential(new RawSeparateTankDrive(0.7, 0.72, 1500));
        addSequential(new Delay(1000));
        addSequential(new RawSeparateTankDrive(0.7, 0.73, 1500));
    }
}
