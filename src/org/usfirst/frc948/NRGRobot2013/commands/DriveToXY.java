package org.usfirst.frc948.NRGRobot2013.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 *
 * @author Paul
 */
public class DriveToXY extends Command {
    
    private double maxPower;        // Motor power to use while driving
    private double xFinal,yFinal;   // Destination coordinates
    private double distanceToGo, prevDistanceToGo;
    private long startTime;
    private final double MIN_START_POWER = 0.3;         // Approximate power needed to get a stationary robot moving
    private final double ACCEL_LIMIT_PER_MSEC = 0.001;
    private final double DRIVEXY_NEAR_TOLERANCE = 14.0 / 12.0;  // How close we're trying to get to target x,y (in feet)
    private final double DRIVEXY_OVERSHOOT_TOLERANCE = 0.1 / 12.0;
    private final double SLOWDOWN_DISTANCE = 2.5;       // Start slowing down at this many feet before the endpoint

    public DriveToXY(double x, double y) {
        this(-0.60, x, y);      // For the Ultimate Ascent game, we always drive backwards!
    }

    /**
     * Drive the robot from its current position to coordinate (x,y) on the field.  Note that the turn rate is limited,
     * so don't expect this command to turn a very sharp angle over a short distance.  In that case you should issue a
     * Turn command before the DriveToXY command.
     * 
     * @param power - max motor power to drive at (negative values drive the robot backwards)
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
        Robot.drive.driveOnHeadingInit();
        prevDistanceToGo = 999999.0;  // any large value will do
        startTime = System.currentTimeMillis();
        
        String curPos = "(" + MathHelper.round(Robot.positionTracker.getX(), 3) + "," + MathHelper.round(Robot.positionTracker.getY(), 3) + ")";
        String dest = "(" + MathHelper.round(xFinal, 3) + "," + MathHelper.round(yFinal, 3) + ")";
        Debug.println("[DriveToXY] starting, drive from " + curPos + " to " + dest);
    }

    protected void execute() {
        double power, newHeading;
        double dX = xFinal - Robot.positionTracker.getX();
        double dY = yFinal - Robot.positionTracker.getY();
        distanceToGo = Math.sqrt(dX * dX + dY * dY);
        // atan2(dy,dx) works in all 4 quadrants; trig angle "theta" will always be between -179.999 and +180.0
        double theta = Math.toDegrees(MathUtils.atan2(dY, dX));
        double gyroHeading = Robot.positionTracker.getHeading();
        
        // Ramp starting power up & ending power down linearly so we don't tip robot over
        double startAccelLimit = (System.currentTimeMillis() - startTime) * ACCEL_LIMIT_PER_MSEC + MIN_START_POWER;
        double endAccelLimit = distanceToGo / SLOWDOWN_DISTANCE;
        double accelLimit = Math.min(startAccelLimit, endAccelLimit);
        if (maxPower >= 0) { // driving forward
            newHeading = 90.0 - theta;
            power = Math.min(accelLimit, maxPower);     //MathHelper.clamp(accelLimit, 0.0, maxPower);
        } else {    // driving backward
            newHeading = -90 - theta;
            power = Math.max(-accelLimit, maxPower);    //MathHelper.clamp(-accelLimit, maxPower, 0.0);
        }
        Robot.drive.driveOnHeading(power, MathHelper.nearestEquivalentHeading(newHeading, gyroHeading));
    }

    protected boolean isFinished() {
        // Finish the command if we're very close to target position, or if we overshot the target and are moving away.
        boolean finished = (distanceToGo <= DRIVEXY_NEAR_TOLERANCE) || (/*distanceToGo < 1.0 &&*/ distanceToGo > prevDistanceToGo + DRIVEXY_OVERSHOOT_TOLERANCE);
        prevDistanceToGo = distanceToGo;
        if (finished) {
            double x = MathHelper.round(Robot.positionTracker.getX(),2);
            double y = MathHelper.round(Robot.positionTracker.getY(),2);
            Debug.println("[DrivetoXY] final pos (" + x + "," + y + ")");
        }
        return finished;
    }

    protected void end() {
        Robot.drive.driveOnHeadingEnd();
        Robot.drive.rawStop();
        // We end up at a not-exactly-predictable heading, so just update the DesiredHeading to whatever direction we finish at.
        Robot.drive.setDesiredHeading(RobotMap.drivegyro.getAngle());
    }

    protected void interrupted() {
        end();
    }
}
