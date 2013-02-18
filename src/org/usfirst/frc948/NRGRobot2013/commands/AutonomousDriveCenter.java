package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author Sean
 */
public class AutonomousDriveCenter extends CommandGroup {

    // TODO: find accurate turning angles and return distances for center location.
    
    public AutonomousDriveCenter() {
        addSequential(new TurnCommand(0.5, -26.5));
        addSequential(new DriveStraightDistance(-0.5, 10.06));
    }
}
