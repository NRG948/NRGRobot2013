// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;

/**
 * @author Patrick & Charles
 */
public class DiscMagazine extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //Solenoids controlling the state of the disc magazine piston
    long timeOfLastShot = 0;
    int count = 0;
    Relay magPiston = RobotMap.magPiston;

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    /**
     * If magPistonOut is set to false, magPistonIn should be set to true, and
     * the piston should be in. If magPistonOut is set to true, magPistonIn
     * should be set to false, and the piston should be out.
     *
     * @return
     */
    //sets the state of magPistonOut to the desired state, and sets the state of magPistonIn to the opposite
    public void openPiston() {
        magPiston.set(Relay.Value.kReverse);
    }

    public void closePiston() {
        magPiston.set(Relay.Value.kOff);
        timeOfLastShot = System.currentTimeMillis();
        count++;
    }

    public long getTimeOfLastShot() {
        if (count == 0 && timeOfLastShot == 0) {
            timeOfLastShot = System.currentTimeMillis();
        }
        return timeOfLastShot;
    }

    public int getCount() {
        return count;
    }
}
