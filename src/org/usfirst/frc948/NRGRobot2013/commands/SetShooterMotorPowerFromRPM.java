package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class SetShooterMotorPowerFromRPM extends Command {
    
    private final double rpm;
    
    public SetShooterMotorPowerFromRPM(double rpm) {
        requires(Robot.shooter);
        
        this.rpm = rpm;
    }

    protected void initialize() {
        
    }

    protected void execute() {
        double effectiveRPM = rpm + Robot.oi.getShootTrimRPM();
        double power = MathHelper.RpmToPower(effectiveRPM);
        
        Robot.shooter.setRawPower(power);
        
        Debug.println("[SetShooterMotorPowerFromRPM] rpm:" + rpm + " trimmed:" + effectiveRPM + ", setting power:" + power);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
    
}
