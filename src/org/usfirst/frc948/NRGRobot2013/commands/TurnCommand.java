package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * Uses PID to turn robot based on gyro heading.
 *
 * @author irving
 */
public class TurnCommand extends PIDCommand {

    public static final double kInitialP = 0.015;
    public static final double kDefaultP = 0.05;
    public static final double kDefaultI = 0.002;
    public static final double kDefaultD = 0.1;
    
    public static final double DEFAULT_DEGREES_TOLERANCE = 2.0;
    public static final int REQUIRED_CYCLES_ON_TARGET = 3;
    
    private final double degrees;
    
    private final double maxLeftPower;
    private final double maxRightPower;
    
    private double pidOutput;
    private int consecutiveCyclesOnTarget;
    
    public TurnCommand(double degreesClockwise) {
        this(degreesClockwise, Math.abs(degreesClockwise) > 180 ? 0.4 : (Math.abs(degreesClockwise) > 50.0 ? 0.5 : 1.0));
    }

    public TurnCommand(double degreesClockwise, double power) {
        this(degreesClockwise, power, power);
    }
    
    public TurnCommand(double degreesClockwise, double maxLeftPower, double maxRightPower) {
        this(degreesClockwise, maxLeftPower, maxRightPower, DEFAULT_DEGREES_TOLERANCE);
    }
    
    public TurnCommand(double degreesClockwise, double maxLeftPower, double maxRightPower, double absoluteTolerance) {
        super(kDefaultP, kDefaultI, kDefaultD);
        
        requires(Robot.drive);
        
        this.maxLeftPower = MathHelper.clamp(maxLeftPower, 0.0, 1.0);
        this.maxRightPower = MathHelper.clamp(maxRightPower, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    protected void initialize() {
        double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, kDefaultP);
        double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, kDefaultI);
        double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, kDefaultD);

        this.getPIDController().setPID(p, i, d);

        Debug.println("[TurnCommand] " + degrees + " degree turn intializing |" +
                " P:" + p +
                " I:" + i +
                " D:" + d +
                " | maxL:" + maxLeftPower + " maxR:" + maxRightPower);

        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
    }

    protected void execute() {
        if (degrees == 0) return;
        
        double leftDrivePower = MathHelper.clamp(pidOutput, -maxLeftPower, maxLeftPower);
        double rightDrivePower = MathHelper.clamp(pidOutput, -maxRightPower, maxRightPower);
        Robot.drive.rawTankDrive(leftDrivePower, -rightDrivePower);
    }

    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println("[TurnCommand] On target (" + consecutiveCyclesOnTarget + "/" + REQUIRED_CYCLES_ON_TARGET + "), Gyro: " + RobotMap.drivegyro.getAngle());
        } else {
            consecutiveCyclesOnTarget = 0;
        }

        return this.degrees == 0 || consecutiveCyclesOnTarget >= REQUIRED_CYCLES_ON_TARGET;
    }

    protected void end() {
        Debug.println("[TurnCommand] end()");
        Robot.drive.rawStop();
    }

    protected void interrupted() {
        end();
    }

    protected double returnPIDInput() {
        return Robot.drive.getGyroAngle();
    }

    protected void usePIDOutput(double d) {
        pidOutput = d;
    }
    
}