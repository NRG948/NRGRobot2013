package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.OperatorShooterSpeed;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * @author Patrick
 */
public class Shooter extends Subsystem {
    
    public static final double MIN_RPM_CLOSE_3PT = 2980;
    public static final double MIN_RPM_FAR_3PT = 2400;  // inside +20
    public static final double MIN_RPM_FAR_2PT = 2300;  // inside +20
    
    public static final double MIN_RPM_MID_COURT = 2280;

    public static final double DEFAULT_OVER_REV = 1.54;
    public static final long SHOOT_DELAY_TIME = 3000;
    
    public double trimCenter = 0.0;
    
    private double overRevFactor = 1.0;
    
    private double lastSetPower = 0;

    public Shooter() {
        super("Shooter");
    }

    public void setRawPower(double power) {
        lastSetPower = power;
        power = MathHelper.clamp(power * overRevFactor, 0, 1.0);
        RobotMap.shooterMotor.set(-power);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorShooterSpeed());
    }

    public void stop() {
        setRawPower(0);
    }

    public void setOverRev(double d) {
        overRevFactor = d;
        setRawPower(lastSetPower);
    }

}
