package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Irving
 */
public class TurnToHeading extends Command {
    
    private final double desiredHeading;
    private double turnAngle;
    
    private double power;
    private final double tolerance;
    
    public TurnToHeading(double heading) {
        this(heading, 0.0);
    }

    public TurnToHeading(double heading, double power) {
        this(heading, power, TurnCommand.DEFAULT_DEGREES_TOLERANCE);
    }
    
    public TurnToHeading(double heading, double power, double degreesTolerance) {
        this.desiredHeading = MathHelper.normalizeAngle(heading);
        this.power = power;
        this.tolerance = degreesTolerance;
    }
    
    protected void initialize() {
        turnAngle = desiredHeading - MathHelper.normalizeAngle(RobotMap.drivegyro.getAngle());
        
        if (turnAngle > 180) {
            turnAngle -= 360;
        } else if (turnAngle < -180) {
            turnAngle += 360;
        }
        
        if (power == 0.0) {
            if (Math.abs(turnAngle) <= 50.0) {
                power = 1.0;
            } else {
                power = 0.5;
            }
        }
    }

    protected void execute() {
        Debug.println("[TurnToHeading]=" + desiredHeading + " starting new Turn(" + power + "," + turnAngle + ")");
        (new TurnCommand(turnAngle, power, power, tolerance)).start();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
