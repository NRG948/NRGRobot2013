/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 *
 * @author Stephen
 */
public class CameraAimAdjust extends PIDCommand {

    double headingCenterOfMassRatio = 30; //assuming the field of vision allows 30 degrees on either side of center
    public static final double kInitialP = 0.015;
    public static final double kDefaultP = 0.05;
    public static final double kDefaultI = 0.001;
    public static final double kDefaultD = 0.1;
    
    public static final double DEFAULT_DEGREES_TOLERANCE = 2.0;
    public static final int REQUIRED_CYCLES_ON_TARGET = 3;
    
    public static final double TARGET_HEADING = 10; //TODO: Calibrate this value using camera readings
    
    private final double maxPower;
    private double pidOutput;
    private int consecutiveCyclesOnTarget;
    
    

    public CameraAimAdjust(double power) {
        this(power, DEFAULT_DEGREES_TOLERANCE);
    }

    public CameraAimAdjust(double maxPower, double absoluteTolerance) {
        super(kDefaultP, kDefaultI, kDefaultD);
        requires(Robot.drive);
        this.maxPower = MathHelper.clamp(maxPower, 0.0, 1.0);
        this.getPIDController().setAbsoluteTolerance(absoluteTolerance);
    }

    protected void initialize() {

        double p = Preferences.getInstance().getDouble(PreferenceKeys.TURN_P, kDefaultP);
        double i = Preferences.getInstance().getDouble(PreferenceKeys.TURN_I, kDefaultI);
        double d = Preferences.getInstance().getDouble(PreferenceKeys.TURN_D, kDefaultD);

        this.getPIDController().setPID(p, i, d);

        Debug.println("[CameraAimAdjust] " + "turning toward target |"
                + " P:" + p
                + " I:" + i
                + " D:" + d
                + " | maxPower:" + maxPower);
        setSetpoint(TARGET_HEADING);
        consecutiveCyclesOnTarget = 0;
    }

    protected void execute() {
        double leftDrivePower = MathHelper.clamp(pidOutput, -maxPower, maxPower);
        Robot.drive.rawTankDrive(leftDrivePower, 0); //Pivoting on right wheel using the left
    }

    protected boolean isFinished() {
        if (this.getPIDController().onTarget()) {
            consecutiveCyclesOnTarget++;
            Debug.println("[CameraAimAdjust] On target (" + consecutiveCyclesOnTarget + "/" + REQUIRED_CYCLES_ON_TARGET);
        } else {
            consecutiveCyclesOnTarget = 0;
        }
        return consecutiveCyclesOnTarget >= REQUIRED_CYCLES_ON_TARGET;
    }

    protected void end() {
        Debug.println("[CameraAimAdjust] end()");
        Robot.drive.rawStop();
    }

    protected void interrupted() {
        end();
    }

    protected double returnPIDInput() {
        try {
            return Robot.camera.getNormalizedCenterOfMass() * headingCenterOfMassRatio;
        } catch (Exception e) {
            return TARGET_HEADING;
        }
    }

    protected void usePIDOutput(double d) {
        pidOutput = d;
    }
}
