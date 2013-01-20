package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Drive;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author irving
 */
public class TurnCommand extends Command {
    
    public static final double kDefaultP = 0.02;
    public static final double kDefaultI = 0.01;
    public static final double kDefaultD = 0.0;
    
    private static final double DEGREES_TOLERANCE = 2.0;

    private double power;
    private double degrees;
    
    private GyroAngleSource gyroAngleSource;
    private PIDRawOutput pidOutput;
    
    private PIDController anglePID;
    
    private int consecutiveCyclesOnTarget;

    public TurnCommand(double power, double degreesClockwise) {
        requires(Robot.drive);

        this.power = MathHelper.clamp(power, 0.0, 1.0);
        this.degrees = degreesClockwise;

        gyroAngleSource = new GyroAngleSource(Robot.drive);
        pidOutput = new PIDRawOutput();
        
        anglePID = new PIDController(kDefaultP, kDefaultI, kDefaultD, gyroAngleSource, pidOutput);
        anglePID.setAbsoluteTolerance(DEGREES_TOLERANCE);
    }
    
    public TurnCommand(double power, double degreesClockwise, double p, double i, double d) {
        requires(Robot.drive);

        this.power = MathHelper.clamp(power, 0.0, 1.0);
        this.degrees = degreesClockwise;

        gyroAngleSource = new GyroAngleSource(Robot.drive);
        pidOutput = new PIDRawOutput();
        
        anglePID = new PIDController(p, i, d, gyroAngleSource, pidOutput);
        anglePID.setAbsoluteTolerance(DEGREES_TOLERANCE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println(power);
        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + degrees);
        anglePID.setSetpoint(Robot.drive.getDesiredHeading());
        anglePID.enable();
        consecutiveCyclesOnTarget = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double drivePower = MathHelper.clamp(pidOutput.getPIDOutput(), -power, power);
        Robot.drive.tankDrive(drivePower, -drivePower);
        
        String setAngle = String.valueOf(MathHelper.round(anglePID.getSetpoint(), 2));
        String errorAngle = String.valueOf(MathHelper.round(anglePID.getError(), 2));
        String drivePowerStr = String.valueOf(MathHelper.round(drivePower, 2));
        
        LCD.clearLine(4);
        LCD.clearLine(5);
        LCD.println(false, 4, "SET:" + setAngle + " ERR:" + errorAngle);
        LCD.println(false, 5, "  PWR:" + drivePowerStr);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (anglePID.onTarget()) {
            consecutiveCyclesOnTarget++;
        } else {
            consecutiveCyclesOnTarget = 0;
        }
        
        return consecutiveCyclesOnTarget >= 20;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drive.tankDrive(0, 0);
        anglePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    private class GyroAngleSource implements PIDSource {

        private Drive drive;

        public GyroAngleSource(Drive drive) {
            this.drive = drive;
        }

        public double pidGet() {
            return drive.getGyroAngle();
        }
    }
    
    private class PIDRawOutput implements PIDOutput {
        
        private double pidOutput;

        public void pidWrite(double d) {
            LCD.println(true, 6, String.valueOf(d) + "   ");
            pidOutput = d;
        }
        
        public double getPIDOutput() {
            return pidOutput;
        }
        
    }
}