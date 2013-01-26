// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc948.NRGRobot2013;

import org.usfirst.frc948.NRGRobot2013.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick leftJoystick;
    public Joystick rightJoystick;
    public AnalogChannel analogShooter;
    public AnalogChannel analogAngle;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    // This represents the io board.
    private static final DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();
    
    private static final double MIN_CYPRESS_VOLTAGE = -0.001; //TODO: Set these values
    private static final double MAX_CYPRESS_VOLTAGE = 3.312;
    private static final int ANALOG_SPEED_CHANNEL = 6;
    private static final double SHOOT_SPEED_DEAD_ZONE = 0.1;
    private static final double MINIMUM_SHOOT_SPEED = 0.2;
    private static final int SHOOTER = 16;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        rightJoystick = new Joystick(1);

        leftJoystick = new Joystick(2);

        analogShooter = new AnalogChannel(3);
        analogAngle = new AnalogChannel(4);

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        SmartDashboard.putData("Turn 90 CW (0.7)", new TurnCommand(0.7, 90));
        SmartDashboard.putData("Turn 90 CW (1.0)", new TurnCommand(1.0, 90));
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getleftJoystick() {
        return leftJoystick;
    }

    public Joystick getrightJoystick() {
        return rightJoystick;
    }

    public AnalogChannel getAnalogShooter() {
        return analogShooter;
    }

    public AnalogChannel getAnalogAngle() {
        return analogAngle;
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    static double getAnalog(int channel) {
        double value = 0.0;
        try {
            value = io.getAnalogIn(channel);
        } catch (EnhancedIOException ex) {
            Debug.println(ex.getMessage());
        }
        return value;
    }

    public static double getRawShootSpeed() {
        double speed = MathHelper.reverseNormalizeValue(getAnalog(ANALOG_SPEED_CHANNEL), MIN_CYPRESS_VOLTAGE, MAX_CYPRESS_VOLTAGE);
        if (speed < SHOOT_SPEED_DEAD_ZONE) {

            speed = 0;
        } else {
            speed = speed * (1 - MINIMUM_SHOOT_SPEED) + MINIMUM_SHOOT_SPEED;
        }
        return speed;
    }

    private static boolean getDigital(int channel) {
        boolean value = false;
        try {
            value = io.getDigital(channel);
        } catch (EnhancedIOException ex) {
            Debug.println(ex.getMessage());
        }
        return value;
    }
}
