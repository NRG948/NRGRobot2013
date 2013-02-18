package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {

    public Autonomous() {
        addSequential(new AutonomousShootFrisbee(3, -0.87));
        addSequential(new AutonomousDriveCenter());
    }
}
