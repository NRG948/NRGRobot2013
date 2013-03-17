package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * 
 * @author irving
 */
public class ShootAtMinRPM extends CommandGroup {
    
    private double rpm;
    
    public ShootAtMinRPM(double rpm) {
        this.rpm = rpm;
        addSequential(new SetShooterMotorPower(MathHelper.RpmToPower(rpm) * 0.85));
        addSequential(new WaitForFrisbee());
        addSequential(new WaitForStartingRPM(rpm - 50));
        addSequential(new SetShooterMotorPower(MathHelper.RpmToPower(rpm) * 1.4));
        addSequential(new WaitForMinRPM(rpm));
        addSequential(new ReleaseFrisbeeCommand());
        addSequential(new SetShooterMotorPower(MathHelper.RpmToPower(rpm)));
        addSequential(new Delay(100));
    }
    
    public void initialize() {
        Debug.println("[ShootAtMinRPM] initializing with RPM=" + rpm);
    }
    
    public void interrupted() {
        (new SetShooterMotorPower(0)).execute();
    }
}
