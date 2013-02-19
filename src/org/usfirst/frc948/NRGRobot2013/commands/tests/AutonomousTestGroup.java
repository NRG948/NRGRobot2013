package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.commands.DriveStraightDistance;
import org.usfirst.frc948.NRGRobot2013.commands.ReleaseFrisbeeCommand;
import org.usfirst.frc948.NRGRobot2013.commands.SetShooterMotorPower;
import org.usfirst.frc948.NRGRobot2013.commands.TurnCommand;
import org.usfirst.frc948.NRGRobot2013.commands.WaitForShooterSpeed;

/**
 *
 * @author irving
 */
public class AutonomousTestGroup extends CommandGroup {
    
    public AutonomousTestGroup() {
        addSequential(new SetShooterMotorPower(1.0));
        addSequential(new ReleaseFrisbeeCommand());
        addSequential(new WaitForShooterSpeed());
        addSequential(new ReleaseFrisbeeCommand());
        addSequential(new WaitForShooterSpeed());
        addSequential(new ReleaseFrisbeeCommand());
        addSequential(new SetShooterMotorPower(0.3));
        addSequential(new TurnCommand(0.5, -26.5));
        addSequential(new DriveStraightDistance(-0.5, 10.06));
    }
}
