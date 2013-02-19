package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Charles
 */
public class AutonomousShootFrisbee extends CommandGroup {

    public AutonomousShootFrisbee(int count, double speed) {
        addSequential(new SetShooterRPM(speed));
        addSequential(new ReleaseFrisbeeCommand());
        for (int i = 0; i < count - 1; i++) {
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
        }
    }
}
