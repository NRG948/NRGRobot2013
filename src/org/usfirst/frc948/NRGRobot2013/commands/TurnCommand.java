package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class TurnCommand extends PIDCommand {
    
    private static final double kDefaultP = 0.02;
    private static final double kDefaultI = 0.01;
    private static final double kDefaultD = 0.0;
    
    private static final double DEGREES_TOLERANCE = 2.0;
    private static final int REQUIRED_CYCLES_ON_TARGET = 20;

    private double power;
    private double degrees;
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
    

    // Called just before this Command runs the first time
    protected void initialize() {
        
        double p = Preferences.getInstance().getDouble("TurnP", kDefaultP);
        this.getPIDController().setPID(p, 0, 0);
        
        Debug.println(Debug.DRIVE, "TurnCommand intializing w/ only P: " + p);
       

        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        setSetpoint(Robot.drive.getDesiredHeading());
        consecutiveCyclesOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!closeToTarget && Math.abs(Robot.drive.getGyroAngle()-Robot.drive.getDesiredHeading())<= 40) {
            closeToTarget = true;
            double p = Preferences.getInstance().getDouble("TurnP", kDefaultP);
            double i = Preferences.getInstance().getDouble("TurnI", kDefaultI);
            double d = Preferences.getInstance().getDouble("TurnD", kDefaultD);
            
            Debug.println(Debug.DRIVE, "TurnCommand after 40 degrees within target: " + p + " " + i + " " + d);
        }
        
        double drivePower = MathHelper.clamp(pidOutput, -power, power);
        Robot.drive.tankDrive(drivePower, -drivePower);
        
        
        String setAngle = String.valueOf(MathHelper.round(getSetpoint(), 2));
        String errorAngle = String.valueOf(MathHelper.round(this.getPIDController().getError(), 2));
        String drivePowerStr = String.valueOf(MathHelper.round(drivePower, 2));
        
        LCD.clearLine(4);
        LCD.clearLine(5);
        LCD.println(LCD.TURNING, 4, "SET:" + setAngle + " ERR:" + errorAngle);
        LCD.println(LCD.TURNING, 5, "  PWR:" + drivePowerStr);
        
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