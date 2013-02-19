package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Charles
 */
public class AutonomousShootFrisbee extends CommandGroup {

    public AutonomousShootFrisbee(int numFrisbee, double shooterRPM) {
        addSequential(new SetShooterRPM(shooterRPM));
        addSequential(new ReleaseFrisbeeCommand());
        for (int i = 0; i < numFrisbee - 1; i++) {
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
        }
    }
}
