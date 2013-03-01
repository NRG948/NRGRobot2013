package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * Uses PID to turn robot based on gyro heading.
 *
 * @author irving
 */
public class TurnCommand extends PIDCommand {

    public static final double kInitialP = 0.015;
    public static final double kDefaultP = 0.02;
    public static final double kDefaultI = 0.01;
    public static final double kDefaultD = 0.0;
    
    private static final double DEGREES_CLOSE = 30.0;
    private static final double DEGREES_TOLERANCE = 2.0;
    private static final int REQUIRED_CYCLES_ON_TARGET = 3;
    
    private double power;
    private double degrees;
    
    private double maxLeftPower;
    private double maxRightPower;
    
    private boolean closeToTarget;
    private double pidOutput;
    private int consecutiveCyclesOnTarget;

    public TurnCommand(double power, double degreesClockwise) {
        super(kDefaultP, kDefaultI, kDefaultD);

        requires(Robot.drive);

        this.closeToTarget = false;
        this.power = MathHelper.clamp(power, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(DEGREES_TOLERANCE);
    }
    
    public TurnCommand(double maxLeftPower, double maxRightPower, double degreesClockwise) {
        super(kDefaultP, kDefaultI, kDefaultD);
        
        requires(Robot.drive);
        
        this.closeToTarget = false;
        this.maxLeftPower = MathHelper.clamp(maxLeftPower, 0.0, 1.0);
        this.maxRightPower = MathHelper.clamp(maxRightPower, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(DEGREES_TOLERANCE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.getPIDController().setPID(kInitialP, 0.0, 0.0);
        this.closeToTarget = false;
        
        Debug.println(Debug.DRIVE, "TurnCommand intializing w/ only P: " + kInitialP);

        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!closeToTarget && Math.abs(Robot.drive.getGyroAngle() - Robot.drive.getDesiredHeading()) <= DEGREES_CLOSE) {
            closeToTarget = true;
            
            double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, kDefaultP);
            double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, kDefaultI);
            double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, kDefaultD);

            Debug.println(Debug.DRIVE, "TurnCommand close to target (" + DEGREES_CLOSE + " degrees)");
            Debug.println(Debug.DRIVE, "TurnCommand adjusting PID constants: " + p + " " + i + " " + d);
            
            this.getPIDController().setPID(p, i, d);
        }
        
//        SmartDashboard.putNumber("Turn ERR", this.getPIDController().getError());

        double drivePower = MathHelper.clamp(pidOutput, -power, power);
        Robot.drive.rawTankDrive(drivePower, -drivePower);
        
//        SmartDashboard.putNumber("Turn SET", drivePower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
        } else {
            consecutiveCyclesOnTarget = 0;
        }

        return consecutiveCyclesOnTarget >= REQUIRED_CYCLES_ON_TARGET;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drive.stop();
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