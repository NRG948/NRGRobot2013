package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Camera;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author Stephen
 */
public class CameraAimAdjust extends PIDCommand {
    
    private final double maxPower;
    private double pidOutput;
    private int consecutiveCyclesOnTarget;

    public CameraAimAdjust(double power) {
        this(power, TurnCommand.DEFAULT_DEGREES_TOLERANCE);
    }

    public CameraAimAdjust(double maxPower, double absoluteTolerance) {
        super(TurnCommand.kDefaultP, TurnCommand.kDefaultI, TurnCommand.kDefaultD, 0.3);
        requires(Robot.drive);
        
        this.maxPower = MathHelper.clamp(maxPower, 0.0, 1.0);
        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    protected void initialize() {
        double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, TurnCommand.kDefaultP);
        double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, TurnCommand.kDefaultI);
        double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, TurnCommand.kDefaultD);

        this.getPIDController().setPID(p, i, d);
        
        this.getPIDController().reset();
        this.getPIDController().enable();

        Debug.println("[CameraAimAdjust] " + "turning toward target |"
                + " P:" + p
                + " I:" + i
                + " D:" + d
                + " | maxPower:" + maxPower);
        
        setSetpoint(Camera.TARGET_CENTER * Camera.FOV);
        
        consecutiveCyclesOnTarget = 0;
    }

    protected void execute() {
        double leftDrivePower = MathHelper.clamp(pidOutput, -maxPower, maxPower);
        Robot.drive.rawTankDrive(leftDrivePower, 0); //Pivoting on right wheel using the left
    }

    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println("[CameraAimAdjust] On target: " + consecutiveCyclesOnTarget + "/" + TurnCommand.REQUIRED_CYCLES_ON_TARGET);
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
        double ret;
        
        try {
            ret = Robot.camera.getNormalizedCenterOfMass() * Camera.FOV;
        } catch (NIVisionException ex) {
            Debug.println("[CameraAimAdjust] caught exception from getNormalizedCenterOfMass()");
            Debug.printException(ex);
            ret = Camera.TARGET_CENTER * Camera.FOV; // if not getting a reading from the camera, stop running
        }
        
        SmartDashboard.putNumber("CameraAimAdjust PIDInput", ret);
        
        return ret;
    }

    protected void usePIDOutput(double d) {
        pidOutput = d;
    }
}
