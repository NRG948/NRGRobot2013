package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.commands.Delay;
import org.usfirst.frc948.NRGRobot2013.commands.TurnCommand;

/**
 * Turns circle in increments of 30 degrees to test turn PID.
 *
 * @author irving
 */
public class IncrementalTurn extends CommandGroup {
    
    public IncrementalTurn(double speed, double degrees, int n) {
        for (int i = 0; i < n; i++) {
            addSequential(new TurnCommand(degrees, speed));
            addSequential(new Delay(1000));
        }
    }
}
