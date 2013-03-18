package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class TurnToHeading extends PIDCommand {
    
    private final double desiredHeading;
    
    private double maxLeftPower;
    private double maxRightPower;
    
    private double pidOutput;
    private int consecutiveCyclesOnTarget;
    
    public TurnToHeading(double heading) {
        this(heading, 0.0);
    }

    public TurnToHeading(double heading, double power) {
        this(heading, power, power);
    }
    
    public TurnToHeading(double heading, double maxLeftPower, double maxRightPower) {
        this(heading, maxLeftPower, maxRightPower, TurnCommand.DEFAULT_DEGREES_TOLERANCE);
    }
    
    public TurnToHeading(double heading, double maxLeftPower, double maxRightPower, double absoluteTolerance) {
        super(TurnCommand.kDefaultP, TurnCommand.kDefaultI, TurnCommand.kDefaultD);
        
        requires(Robot.drive);
        
        this.desiredHeading = MathHelper.normalizeAngle(heading);
        this.maxLeftPower = MathHelper.clamp(maxLeftPower, 0.0, 1.0);
        this.maxRightPower = MathHelper.clamp(maxRightPower, 0.0, 1.0);

        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    protected void initialize() {
//        Debug.println("[TurnToHeading] initialize(), getDesiredHeading()=" + Robot.drive.getDesiredHeading());
        
        double turnAngle = desiredHeading - MathHelper.normalizeAngle(Robot.drive.getDesiredHeading());
        
        if (turnAngle > 180) {
            turnAngle -= 360;
        } else if (turnAngle < -180) {
            turnAngle += 360;
        }
        
        if (maxLeftPower == 0.0 && maxRightPower == 0.0) {
            if (turnAngle > 50.0) {
                maxLeftPower = 0.5;
                maxRightPower = 0.5;
            } else {
                maxLeftPower = 1.0;
                maxRightPower = 1.0;
            }
        }
        
//        Debug.println("[TurnToHeading] turnAngle=" + turnAngle);

        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + turnAngle);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
        
        Debug.println(Debug.DRIVE, "[TurnToHeading] to " + Robot.drive.getDesiredHeading() + " degrees (" + desiredHeading + ") from " + RobotMap.drivegyro.getAngle() + ", maxL: " + maxLeftPower + " maxR:" + maxRightPower);
    }

    protected void execute() {
        double leftDrivePower = MathHelper.clamp(pidOutput, -maxLeftPower, maxLeftPower);
        double rightDrivePower = MathHelper.clamp(pidOutput, -maxRightPower, maxRightPower);
        
        Robot.drive.rawTankDrive(leftDrivePower, -rightDrivePower);
    }

    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println(Debug.DRIVE, "[TurnToHeading] ON TARGET, Gyro: " + RobotMap.drivegyro.getAngle());
        } else {
            consecutiveCyclesOnTarget = 0;
        }

        return consecutiveCyclesOnTarget >= TurnCommand.REQUIRED_CYCLES_ON_TARGET;
    }

    protected void end() {
        Debug.println("[TurnToHeading] end()");
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
