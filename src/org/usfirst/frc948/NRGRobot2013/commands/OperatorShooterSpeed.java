package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * Default command for Shooter subsystem.
 * 
 * @author Kevin & Patrick
 */
public class OperatorShooterSpeed extends Command {

    public OperatorShooterSpeed() {
        requires(Robot.shooter);
    }

    protected void initialize() {
        Debug.println("[OperatorShooterSpeed] initializing");
    }

    protected void execute() {
        double speedSlider = Robot.oi.getRawShootSpeed();
        double speed = speedSlider + Robot.oi.getShootTrimPower();
        
        SmartDashboard.putNumber("manual shoot speed", speed);
        Robot.shooter.setRawPower(speed);
        
        LCD.clearLine(5);
        LCD.println(LCD.SHOOTER, 5, "SHT RAW:" + MathHelper.round(speed, 2) + " EST:" + ((int) MathHelper.PowerToRpm(speed)));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Debug.println("[OperatorShooterSpeed] ending");
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
}