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

import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
    public Joystick leftJoystick = new Joystick(1);
    public Joystick rightJoystick = new Joystick(2);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    // This represents the io board.
    private static final DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();
    
    private static final double MIN_CYPRESS_VOLTAGE = -0.001; //TODO: Set these values
    private static final double MAX_CYPRESS_VOLTAGE = 3.312;
    private static final int ANALOG_SPEED_CHANNEL = 6;
    private static final double SHOOT_SPEED_DEAD_ZONE = 0.1;
    private static final double MINIMUM_SHOOT_SPEED = 0.2;
    
    public Button leftJoyBtn1 = new JoystickButton(leftJoystick, 1),
           leftJoyBtn2 = new JoystickButton(leftJoystick, 2),
           leftJoyBtn3 = new JoystickButton(leftJoystick, 3),
           leftJoyBtn4 = new JoystickButton(leftJoystick, 4),
           leftJoyBtn5 = new JoystickButton(leftJoystick, 5),
           leftJoyBtn6 = new JoystickButton(leftJoystick, 6),
           leftJoyBtn7 = new JoystickButton(leftJoystick, 7),
           leftJoyBtn8 = new JoystickButton(leftJoystick, 8),
           leftJoyBtn9 = new JoystickButton(leftJoystick, 9),
           leftJoyBtn10 = new JoystickButton(leftJoystick, 10),
           leftJoyBtn11 = new JoystickButton(leftJoystick, 11),
           leftJoyBtn12 = new JoystickButton(leftJoystick, 12),
            
           rightJoyBtn1 = new JoystickButton(rightJoystick, 1),
           rightJoyBtn2 = new JoystickButton(rightJoystick, 2),
           rightJoyBtn3 = new JoystickButton(rightJoystick, 3),
           rightJoyBtn4 = new JoystickButton(rightJoystick, 4),
           rightJoyBtn5 = new JoystickButton(rightJoystick, 5),
           rightJoyBtn6 = new JoystickButton(rightJoystick, 6),
           rightJoyBtn7 = new JoystickButton(rightJoystick, 7),
           rightJoyBtn8 = new JoystickButton(rightJoystick, 8),
           rightJoyBtn9 = new JoystickButton(rightJoystick, 9),
           rightJoyBtn10 = new JoystickButton(rightJoystick, 10),
           rightJoyBtn11 = new JoystickButton(rightJoystick, 11);
    
    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        leftJoyBtn7.whenPressed(new ResetGyroCommand());
        leftJoyBtn11.whenPressed(new ReadGyroSensitivity());
        
        rightJoyBtn1.whenPressed(new ReleaseFrisbeeCommand());
        rightJoyBtn10.whileHeld(new ClimbDownCommand());
        rightJoyBtn11.whileHeld(new ClimbUpCommand(0.5));
        
        SmartDashboard.putData("Turn 90 CW (0.7)", new TurnCommand(0.7, 90));
        SmartDashboard.putData("DriveStraight 1 sec (0.5)", new DriveStraightTime(0.5, 1000));
        SmartDashboard.putData("DriveStraight 2 sec (0.5)", new DriveStraightTime(0.5, 2000));

        SmartDashboard.putData("DrStrghtTime 15sec (0.5)", new DriveStraightTime(0.5, 15000));
        SmartDashboard.putData("Drive 10 feet", new DriveStraightDistance(0.5, 10));
        SmartDashboard.putData("Drive 10 feet full speed", new DriveStraightDistance(1, 10));
        SmartDashboard.putData("Drive 10 feet back 0.5 power", new DriveStraightDistance(-0.5, 10));

        SmartDashboard.putData("RawTankDrive 2 sec (0.5)", new RawTankDrive(0.5, 2000));
        SmartDashboard.putData("turn 1800 CW (0.7)", new TurnCommand(0.7, 1800));
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getleftJoystick() {
        return leftJoystick;
    }

    public Joystick getrightJoystick() {
        return rightJoystick;
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    private static double getAnalog(int channel) {
        double value = 0.0;
        try {
            value = io.getAnalogIn(channel);
        } catch (EnhancedIOException ex) {
            Debug.printException(ex);
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
            Debug.printException(ex);
        }
        return value;
    }
}
