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
    /*
     * rough turns: 0.01 0.001 0.1
     * 0.05 0.001 0.1
     * small turn: 1.0 power
     * big turn: 0.6 power
     * really big turn: 0.4 power
     */
    public static final double kInitialP = 0.015;
    public static final double kDefaultP = 0.05;
    public static final double kDefaultI = 0.001;
    public static final double kDefaultD = 0.1;
    
    public static final double DEFAULT_DEGREES_TOLERANCE = 2.0;
    
    private static final double DEGREES_CLOSE = 10.0;
    public static final int REQUIRED_CYCLES_ON_TARGET = 3;
    
    private final double degrees;
    
    private final double maxLeftPower;
    private final double maxRightPower;
    
    private boolean closeToTarget;
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
        
        this.closeToTarget = false;
        this.maxLeftPower = MathHelper.clamp(maxLeftPower, 0.0, 1.0);
        this.maxRightPower = MathHelper.clamp(maxRightPower, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.getPIDController().setPID(kInitialP, 0.0, 0.0);
        this.closeToTarget = false;
        
        Debug.println(Debug.DRIVE, "[TurnCommand] " + degrees + " degrees, intializing w/ only P: " + kInitialP + ", maxPowers: " + maxLeftPower + " " + maxRightPower);

        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (degrees == 0) return;
        
//        if (!closeToTarget && Math.abs(Robot.drive.getGyroAngle() - Robot.drive.getDesiredHeading()) <= DEGREES_CLOSE) {
//            closeToTarget = true;
            
            double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, kDefaultP);
            double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, kDefaultI);
            double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, kDefaultD);

//            Debug.println(Debug.DRIVE, "TurnCommand close to target (" + DEGREES_CLOSE + " degrees)");
//            Debug.println(Debug.DRIVE, "    adjusting PID constants: " + p + " " + i + " " + d);
            
            this.getPIDController().setPID(p, i, d);
//        }
        
//        SmartDashboard.putNumber("Turn ERR", this.getPIDController().getError());

        double leftDrivePower = MathHelper.clamp(pidOutput, -maxLeftPower, maxLeftPower);
        double rightDrivePower = MathHelper.clamp(pidOutput, -maxRightPower, maxRightPower);
        Robot.drive.rawTankDrive(leftDrivePower, -rightDrivePower);
        
//        SmartDashboard.putNumber("Turn SET", drivePower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println(Debug.DRIVE, "TurnCommand ON TARGET, Gyro: " + RobotMap.drivegyro.getAngle());
        } else {
            consecutiveCyclesOnTarget = 0;
        }

        return this.degrees == 0 || consecutiveCyclesOnTarget >= REQUIRED_CYCLES_ON_TARGET;
    }

    // Called once after isFinished returns true
    protected void end() {
        Debug.println("[TurnCommand] end()");
        Robot.drive.rawStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
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