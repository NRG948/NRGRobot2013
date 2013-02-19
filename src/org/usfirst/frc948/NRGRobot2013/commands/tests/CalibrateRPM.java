package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
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
        
        if((System.currentTimeMillis() - startingTime) >= 30000) {
            power += 0.05;
            startingTime = System.currentTimeMillis();
            System.out.println(RobotMap.shooterQuadrature.averageRPM());
            System.out.println(power);
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
