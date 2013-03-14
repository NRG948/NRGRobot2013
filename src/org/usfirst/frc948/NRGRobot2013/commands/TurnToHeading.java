package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Irving
 */
public class TurnToHeading extends Command {
    
    private final double power;
    private final double desiredHeading;
    
    public TurnToHeading(double power, double heading) {
        this.power = power;
        this.desiredHeading = MathHelper.normalizeAngle(heading);
    }

    protected void initialize() {
    }

    protected void execute() {
        double turnAngle = desiredHeading - MathHelper.normalizeAngle(RobotMap.drivegyro.getAngle());
        
        if (turnAngle > 180) {
            turnAngle -= 360;
        } else if (turnAngle < -180) {
            turnAngle += 360;
        }
        
        (new TurnCommand(power, turnAngle)).start();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
