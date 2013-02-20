package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * Gathers data to run a linear regression on RPM-power function.
 * Function is used to estimate input motor power for given RPM.
 *
 * @author holeung
 */
public class CalibrateRPM extends Command {

    private double power;
    private double startingTime;

    public CalibrateRPM() {
        requires(Robot.shooter);
    }

    protected void initialize() {
        power = 0.2;
        startingTime = System.currentTimeMillis();
    }

    protected void execute() {
        if ((System.currentTimeMillis() - startingTime) >= 30000) {
            System.out.print("POWER:");
            System.out.print(MathHelper.round(power, 2));
            System.out.print(" AVG_RPM:");
            System.out.print(MathHelper.round(RobotMap.shooterQuadrature.averageRPM(), 4));
            System.out.print(" BATT_VOLT:");
            System.out.print(DriverStation.getInstance().getBatteryVoltage());
            System.out.println();

            startingTime = System.currentTimeMillis();
            power += 0.05;
        }

        Robot.shooter.setRawPower(power);
    }

    protected boolean isFinished() {
        return power > 1;
    }

    protected void end() {
        Robot.shooter.setRawPower(0);
    }

    protected void interrupted() {
        end();
    }
}
