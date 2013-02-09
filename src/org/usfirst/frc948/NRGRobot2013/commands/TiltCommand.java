package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.DiscMagazine;

/**
 * Empty as of RIGHT NOW, we have not determined our mechanism to lean forward
 * on the edge
 *
 * @author Charles
 */
public class TiltCommand extends Command {

    Relay piston = RobotMap.magPiston;
    DiscMagazine mag;

    protected void initialize() {
        mag = new DiscMagazine();
    }

    protected void execute() {
        try {
            mag.activateTilt();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
