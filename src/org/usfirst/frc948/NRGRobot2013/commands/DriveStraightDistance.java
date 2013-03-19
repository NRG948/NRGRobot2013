package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Sean
 */
public class DriveStraightDistance extends Command {

    private final double speed;
    private final double distance;
    
    private double leftStartDistance;
    private double rightStartDistance;
    private double greaterValue;
    
    public DriveStraightDistance(double speed, double distance) {
        requires(Robot.drive);
        this.speed = speed;
        this.distance = distance;
    }

    protected void initialize() {
        leftStartDistance = Robot.drive.getLeftQuadratureDistance();
        rightStartDistance = Robot.drive.getRightQuadratureDistance();
        
        Robot.drive.driveStraightInit();
        
        Debug.println("[DriveStraightDistance] driving " + distance + " feet at " + speed + " power");
    }

    protected void execute() {
        double leftDistanceTraveled = Math.abs(Robot.drive.getLeftQuadratureDistance()-leftStartDistance);
        double rightDistanceTraveled = Math.abs(Robot.drive.getRightQuadratureDistance()-rightStartDistance);

        greaterValue = Math.max(leftDistanceTraveled, rightDistanceTraveled);
        double distanceRemaining = distance - greaterValue;
        if (speed > 0) {
            Robot.drive.driveStraight(MathHelper.clamp(distanceRemaining / 2, -Math.abs(speed), Math.abs(speed)),
                    Robot.drive.getDesiredHeading());
        } else if (speed < 0) {
            Robot.drive.driveStraight(MathHelper.clamp(-distanceRemaining / 2, -Math.abs(speed), Math.abs(speed)),
                    Robot.drive.getDesiredHeading());
        }

    }

    protected boolean isFinished() {
        return greaterValue >= distance;
    }

    protected void end() {
        Debug.println("[DriveStraightDistance] end()");
        Robot.drive.driveStraightEnd();
        Robot.drive.rawStop();
    }

    protected void interrupted() {
        end();
    }
}
