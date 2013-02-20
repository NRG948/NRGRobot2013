package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.commands.OperatorShooterSpeed;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * A shooter utility without PID
 *
 * @author Patrick
 */
public class Shooter extends PIDSubsystem {
    
    public static final double MIN_RPM_CLOSE_SHOT = 2900;
    public static final double MIN_RPM_FAR_SHOT = 2350;

    public static final boolean DEFAULT_USE_PID = false;
    public static final double kDefaultP = 0.001;
    public static final double kDefaultI = kDefaultP / 10;
    public static final double kDefaultD = 0.0;
    public static final double PID_OUTPUT_SCALE_VALUE = 0.01;
    private static final double pidDeactivationConstant = 1000000000;
    public static final double DEFAULT_OVER_REV = 1.10;
    public static final long SHOOT_DELAY_TIME = 3000;
    private double currentMotorPower = 0;
    private double overRevFactor = 1.0;
    private double kP = kDefaultP;
    private double kI = kDefaultI;
    private double kD = kDefaultD;
    private double pidOutputScaleValue = PID_OUTPUT_SCALE_VALUE;
    private boolean pidEnabled = false;
    private boolean largeError = true;


    public Shooter() {
        super("Shooter", kDefaultP, kDefaultI, kDefaultD);
        
        setPercentTolerance(1.0);
        this.getPIDController().setInputRange(0, 3800);
        this.enable();
    }

    public void setRawPower(double power) {
        power = MathHelper.clamp(power * overRevFactor, 0, 1.0);
        RobotMap.shooterMotor.set(-power);
        currentMotorPower = power;
    }

    public void setDesiredRPM(double rpm) {
//            if (Math.abs(this.getSetpoint() - speed) / this.getSetpoint() > 0.5) {
//                this.getPIDController().reset(); //note: reset writes zero to the PIDoutput
//            }
        SmartDashboard.putNumber("shooter setRpm", rpm);

        kP = Preferences.getInstance().getDouble(PreferenceKeys.SHOOTER_P, kDefaultP);
        kI = Preferences.getInstance().getDouble(PreferenceKeys.SHOOTER_I, kDefaultI);
        kD = Preferences.getInstance().getDouble(PreferenceKeys.SHOOTER_D, kDefaultD);
        pidOutputScaleValue = Preferences.getInstance().getDouble(PreferenceKeys.SHOOT_PID_SCALE_FACTOR, PID_OUTPUT_SCALE_VALUE);

        this.getPIDController().setPID(kP, kI, kD);
        this.enable();
        this.setSetpoint(rpm);
        SmartDashboard.putNumber("Desired RPM", this.getSetpoint());
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new OperatorShooterSpeed());
        SmartDashboard.putData("Shooter", this);
        SmartDashboard.putData("ShooterPID", this.getPIDController());
    }

    protected double returnPIDInput() {
        double RPM = RobotMap.shooterQuadrature.getRPM();
        return RPM;
    }

    public void setPidState(boolean state) {
        this.pidEnabled = state;
    }

    protected void usePIDOutput(double output) {
        if (pidEnabled) {
            PIDController pid = this.getPIDController();
            
            double currentError = pid.getError();
            double desiredRPM = pid.getSetpoint();
            
            SmartDashboard.putNumber("Shooter PID set", desiredRPM);
            SmartDashboard.putNumber("Shooter PID out", output);
            SmartDashboard.putNumber("Shooter PID err", currentError);

            if (Math.abs(currentError) > pidDeactivationConstant) {
                largeError = true;
                if (currentError > 0) {
                    setRawPower(1.0);
                } else {
                    setRawPower(0.0);
                }
            } else {
                double newPower, approxPower = MathHelper.RpmToPower(desiredRPM);
                
                if (largeError) {
                    // The first time in true PID mode, make a best guess at the desired power.
                    newPower = approxPower;
                    largeError = false;
                } else {
                    newPower = currentMotorPower + output * pidOutputScaleValue;
                }
                
                newPower = MathHelper.clamp(newPower, approxPower - 0.03, approxPower + 0.03);
                setRawPower(newPower);
            }
        }
    }

    public void stop() {
        this.disable();
        setRawPower(0);
    }

    public void setOverRev(double d) {
        overRevFactor = d;
    }

    public void reset() {
        this.getPIDController().reset();
        this.getPIDController().enable();
    }
}
