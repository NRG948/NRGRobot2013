package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class TurnCommand extends PIDCommand {
    
    public static final double kDefaultP = 0.02;
    public static final double kDefaultI = 0.01;
    public static final double kDefaultD = 0.0;
    
    private static final double DEGREES_TOLERANCE = 2.0;
    private static final int REQUIRED_CYCLES_ON_TARGET = 20;

    private double power;
    private double degrees;
    
    private double pidOutput;
    
    private int consecutiveCyclesOnTarget;

    public TurnCommand(double power, double degreesClockwise) {
        super(kDefaultP, kDefaultI, kDefaultD);
        
        requires(Robot.drive);

        this.power = MathHelper.clamp(power, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(DEGREES_TOLERANCE);
    }
    
    public TurnCommand(double power, double degreesClockwise, double p, double i, double d) {
        super(p, i, d);
        
        requires(Robot.drive);

        this.power = MathHelper.clamp(power, 0.0, 1.0);
        this.degrees = degreesClockwise;

        this.getPIDController().setAbsoluteTolerance(DEGREES_TOLERANCE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println(power);
        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double drivePower = MathHelper.clamp(pidOutput, -power, power);
        Robot.drive.tankDrive(drivePower, -drivePower);
        
        String setAngle = String.valueOf(MathHelper.round(getSetpoint(), 2));
        String errorAngle = String.valueOf(MathHelper.round(this.getPIDController().getError(), 2));
        String drivePowerStr = String.valueOf(MathHelper.round(drivePower, 2));
        
        LCD.clearLine(4);
        LCD.clearLine(5);
        LCD.println(false, 4, "SET:" + setAngle + " ERR:" + errorAngle);
        LCD.println(false, 5, "  PWR:" + drivePowerStr);
        
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
        Robot.drive.tankDrive(0, 0);
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