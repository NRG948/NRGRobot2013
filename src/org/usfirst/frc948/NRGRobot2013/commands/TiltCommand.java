package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.subsystems.DiscMagazine;

/**
 * Empty as of RIGHT NOW, we have not determined our mechanism to lean forward
 * on the edge
 * UPDATE: PISTON to pump forward
 * @author Charles
 */
public class TiltCommand extends Command {

    
    protected void initialize() {
      
    }

    protected void execute() {
        try {
            Robot.climber.activateTilt();
        } catch (InterruptedException ex) {
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
