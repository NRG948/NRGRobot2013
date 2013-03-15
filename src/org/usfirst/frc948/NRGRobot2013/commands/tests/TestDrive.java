package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.commands.Delay;
import org.usfirst.frc948.NRGRobot2013.commands.DriveToXY;
import org.usfirst.frc948.NRGRobot2013.commands.TurnToHeading;

/**
 *
 * @author irving
 */
public class TestDrive extends CommandGroup {
    
    public TestDrive() {
        addSequential(new DriveToXY(-0.3, 0, -7));
        addSequential(new DriveToXY(-0.3, 5, -14));
        addSequential(new DriveToXY(-0.3, 5, -20));
        addSequential(new Delay(5000));
        addSequential(new DriveToXY(0.3, 0, 0));
        addSequential(new TurnToHeading(0.2, 0));
    }
    
}
