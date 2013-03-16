package org.usfirst.frc948.NRGRobot2013.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Paul
 */
public class DriveToXY extends Command {
    
    private double maxPower;        // Motor power to use while driving
    private double xFinal,yFinal;   // Destination coordinates
    private double distanceToGo, prevDistanceToGo;
    private final double DRIVEXY_TOLERANCE = 1.0/12.0;  // How close we're trying to get to target x,y (in feet)

    public DriveToXY(double x, double y) {
        this(0.45, x, y);
    }

    /*
     * @param power - max motor power to drive at (negative values move the robot backwards)
     * @param x - desired destination x coordinate
     * @param y - desired destination y coordinate
     */
    public DriveToXY(double power, double x, double y) {
        requires(Robot.drive);
        maxPower = power;
        xFinal = x;
        yFinal = y;
    }

    protected void initialize() {
        Robot.drive.driveStraightInit();
        prevDistanceToGo = 999999.0;  // any large value will do
    }

    protected void execute() {
        double power, newHeading;
        double dX = xFinal - Robot.positionTracker.getX();
        double dY = yFinal - Robot.positionTracker.getY();
        distanceToGo = Math.sqrt(dX * dX + dY * dY);
        // atan2(dy,dx) works in all 4 quadrants; trig angle "theta" will always be between -179.999 and +180.0
        double theta = Math.toDegrees(MathUtils.atan2(dY, dX));
        double gyroHeading = Robot.positionTracker.getHeading();
        
        if (maxPower >= 0) { // driving forward
            newHeading = 90.0 - theta;
            power = MathHelper.clamp(distanceToGo / 3, 0.0, maxPower);
        } else {    // driving backward
            newHeading = -90 - theta;
            power = MathHelper.clamp(-distanceToGo / 3, maxPower, 0.0);
        }
        Robot.drive.driveStraight(power, equivalentHeading(newHeading, gyroHeading));
    }

    private double equivalentHeading(double heading, double gyroHeading) {
        double deltaAngle = (heading - gyroHeading) % 360;
        if (deltaAngle > 180) deltaAngle -= 360;
        if (deltaAngle <= -180) deltaAngle += 360;
        return gyroHeading + deltaAngle;
    }
    
    protected boolean isFinished() {
        // Finish the command if we're very close to target position, or if we overshot the target and are moving away.
        boolean finished = (distanceToGo <= DRIVEXY_TOLERANCE) || (distanceToGo < 1.0 && distanceToGo > prevDistanceToGo + DRIVEXY_TOLERANCE/2);
        prevDistanceToGo = distanceToGo;
        double x = MathHelper.round(Robot.positionTracker.getX(),2);
        double y = MathHelper.round(Robot.positionTracker.getY(),2);
        System.out.println("[DrivetoXY] final pos (" + x + "," + y + ")");
        return finished;
    }

    protected void end() {
        Robot.drive.driveStraightEnd();
        Robot.drive.rawStop();
        // We end up at a not-exactly-predictable heading, so just update the DesiredHeading to whatever direction we finish at.
        Robot.drive.setDesiredHeading(RobotMap.drivegyro.getAngle());
    }

    protected void interrupted() {
        end();
    }
}
