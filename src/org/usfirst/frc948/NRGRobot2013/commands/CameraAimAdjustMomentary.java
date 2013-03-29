package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.image.NIVisionException;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.Camera;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author Stephen
 */
public class CameraAimAdjustMomentary extends PIDCommand {
    
    private final double maxPower;
    private double pidOutput;
    private int consecutiveCyclesOnTarget;

    public CameraAimAdjustMomentary(double power) {
        this(power, TurnCommand.DEFAULT_DEGREES_TOLERANCE);
    }

    public CameraAimAdjustMomentary(double maxPower, double absoluteTolerance) {
        super(TurnCommand.kDefaultP, TurnCommand.kDefaultI, TurnCommand.kDefaultD, 0.3);
        requires(Robot.drive);
        
        this.maxPower = MathHelper.clamp(maxPower, 0.0, 1.0);
        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    protected void initialize() {
        this.getPIDController().reset();
        
        double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, TurnCommand.kDefaultP);
        double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, TurnCommand.kDefaultI);
        double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, TurnCommand.kDefaultD);

        this.getPIDController().setPID(p, i, d);
        
        double turnAngle;
        try {
            turnAngle = Camera.FOV * (Robot.camera.getNormalizedCenterOfMass() - Camera.TARGET_CENTER);
        } catch (NIVisionException ex) {
            turnAngle = 0;
            Debug.printException(ex);
        }
        
//        Robot.drive.setDesiredHeading(Robot.drive.getDesiredHeading() + turnAngle);
        Robot.drive.setDesiredHeading(RobotMap.drivegyro.getAngle() + turnAngle);
        this.setSetpoint(Robot.drive.getDesiredHeading());

        Debug.println("[CameraAimAdjustMomentary] " + "turning toward target " + turnAngle + " degrees CW |"
                + " P:" + p
                + " I:" + i
                + " D:" + d
                + " | maxPower:" + maxPower);
        
        this.getPIDController().enable();
        consecutiveCyclesOnTarget = 0;
    }

    protected void execute() {
        double leftDrivePower = MathHelper.clamp(pidOutput, -maxPower, maxPower);
        Robot.drive.rawTankDrive(leftDrivePower, 0); //Pivoting on right wheel using the left
    }

    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println("[CameraAimAdjustMomentary] On target: " + consecutiveCyclesOnTarget + "/" + TurnCommand.REQUIRED_CYCLES_ON_TARGET);
        } else {
            consecutiveCyclesOnTarget = 0;
        }
        return consecutiveCyclesOnTarget >= TurnCommand.REQUIRED_CYCLES_ON_TARGET;
    }

    protected void end() {
//        Debug.println("[CameraAimAdjust] end()");
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
