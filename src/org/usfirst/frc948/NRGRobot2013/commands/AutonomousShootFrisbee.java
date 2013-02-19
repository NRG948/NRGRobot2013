package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Charles
 */
public class AutonomousShootFrisbee extends CommandGroup {

    public AutonomousShootFrisbee(int numFrisbee, double shooterRPM) {
        addSequential(new SetShooterRPM(shooterRPM));
        for (int i = 0; i < numFrisbee; i++) {
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
        }
    }
}
