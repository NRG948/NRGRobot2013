package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * Waits for minimum RPM before shooting.
 * ...aka god-mode.
 * 
 * @author irving
 */
public class ShootAtMinRPM extends CommandGroup {
    
    private double rpm;
    
    public ShootAtMinRPM(double rpm) {
        this.rpm = rpm;
        addSequential(new SetShooterMotorPower(MathHelper.RpmToPower(rpm) * 1.3));
        // addSequential(new SetShooterMotorPower(1.0));  // this is too fast!
        addSequential(new WaitForMinRPM(rpm));
        addSequential(new ReleaseFrisbeeCommand());
        addSequential(new SetShooterMotorPower(MathHelper.RpmToPower(rpm)));
    }
    
    public void initialize() {
        System.out.println("[ShootAtMinRPM] initializing with RPM=" + rpm);
    }
    
    public void interrupted() {
        (new SetShooterMotorPower(0)).execute();
    }
}
