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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import org.usfirst.frc948.NRGRobot2013.commands.tests.BalanceTankDrive;
import org.usfirst.frc948.NRGRobot2013.commands.tests.CalibrateRPM;
import org.usfirst.frc948.NRGRobot2013.commands.tests.IncrementalTurn;
import org.usfirst.frc948.NRGRobot2013.subsystems.Climber;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements IOperatorInterface {
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
    
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    
    // This represents the io board.
    private final DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();
    
    private static final double MIN_CYPRESS_VOLTAGE = -0.001; //TODO: Set these values
    private static final double MAX_CYPRESS_VOLTAGE = 3.312;
    
    private static final int AUTONOMOUS_SHOOT_SWITCH_CHANNEL_1 = 1;
    private static final int AUTONOMOUS_SHOOT_SWITCH_CHANNEL_2 = 3;
    private static final int AUTONOMOUS_DRIVE_SWITCH_CHANNEL_1 = 5;
    private static final int AUTONOMOUS_DRIVE_SWITCH_CHANNEL_2 = 7;
    
    private static final int FULL_AUTONOMOUS_SWITCH_CHANNEL = 11;
    
    private static final int CAMERA_SLIDER_CHANNEL = 2;
    private static final int TRIM_SLIDER_CHANNEL = 4;
    private static final int SPEED_SLIDER_CHANNEL = 6;
    
    private static final double SHOOT_SPEED_DEAD_ZONE = 0.1;
    private static final double MINIMUM_SHOOT_SPEED = 0.0;
    private static final double SHOOT_TRIM_MAX_POWER = 0.2;
    private static final double SHOOT_TRIM_MAX_RPM = 200;
    
    private Button leftJoyBtn1 = new JoystickButton(leftJoystick, 1),
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
    
    private Button btnClimbDisengage = new DigitalIOButton(12);
    private Button btnClimbEngage =  new DigitalIOButton(14);
    
    private Button shootButton = new DigitalIOButton(16);
    
    private Button btnShootClose1 = new DigitalIOButton(4);
    private Button btnShootClose2 = new DigitalIOButton(6);
    private Button btnShootFar1 = new DigitalIOButton(8);
    private Button btnShootFar2 = new DigitalIOButton(10);
    
    private double shootTrim = 0.0;
    
    public OI() {
        leftJoyBtn7.whenPressed(new ResetSensorsCommand());
        leftJoyBtn11.whenPressed(new ReadGyroSensitivity());
        leftJoyBtn12.whenPressed(new InitializePreferences());
        
        shootButton.whenPressed(new ReleaseFrisbeeCommand());
        
        rightJoyBtn6.whileHeld(new ClimbCommand(Climber.Direction.kUp));
        
        btnClimbDisengage.whenPressed(new TiltCommand(false));
        btnClimbEngage.whenPressed(new TiltCommand(true));
        
//        btnShootClose1.whenPressed(new AutonomousShoot());
        btnShootClose2.whileHeld(new ShootAtMinRPM(Shooter.MIN_RPM_CLOSE_3PT));
        btnShootFar1.whileHeld(new ShootAtMinRPM(Shooter.MIN_RPM_FAR_3PT));
        btnShootFar2.whileHeld(new ShootAtMinRPM(Shooter.MIN_RPM_FAR_2PT));
        
        SmartDashboard.putData("Turn 15 CW (0.3)", new TurnCommand(15, 0.3));
        SmartDashboard.putData("Turn 90 CW (0.3)", new TurnCommand(90, 0.3));
//        SmartDashboard.putData("Turn 1800 CW (0.5)", new TurnCommand(0.5, 1800));
        SmartDashboard.putData("IncrementalTurn (0.3, 45, 8)", new IncrementalTurn(0.3, 45, 8));
//        
        SmartDashboard.putData("Drive 10 feet (0.4)", new DriveStraightDistance(0.4, 10));
        SmartDashboard.putData("Drive 10 feet (-0.4)", new DriveStraightDistance(-0.4, 10));
//        
//        SmartDashboard.putData("BalanceTankDrive", new BalanceTankDrive());
//        
//        SmartDashboard.putData("Autonomous (Timer)", new Autonomous(Autonomous.ShooterMode.kTimer, Autonomous.StartingPosition.kCenter, Autonomous.TargetPosition.kNone));
//        SmartDashboard.putData("Autonomous (Encoder)", new Autonomous(Autonomous.ShooterMode.kEncoder, Autonomous.StartingPosition.kCenter, Autonomous.TargetPosition.kNone));
//        SmartDashboard.putData("Autonomous (PID)", new Autonomous(Autonomous.ShooterMode.kPID, Autonomous.StartingPosition.kCenter, Autonomous.TargetPosition.kNone));
//        
        SmartDashboard.putData("CalibrateRPM", new CalibrateRPM());
        
        SmartDashboard.putData("DriveToXY(Params)", new DriveToXYParams());
    }
    
    public Joystick getleftJoystick() {
        return leftJoystick;
    }

    public Joystick getrightJoystick() {
        return rightJoystick;
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    
    public double getCameraTilt() {
        double angle = MathHelper.reverseNormalizeValue(getAnalog(CAMERA_SLIDER_CHANNEL), MIN_CYPRESS_VOLTAGE, MAX_CYPRESS_VOLTAGE);
        return angle;
    }

    private double getAnalog(int channel) {
        double value = 0.0;
        try {
            value = io.getAnalogIn(channel);
        } catch (EnhancedIOException ex) {
            Debug.printException(ex);
        }
        return value;
    }

    public double getRawShootSpeed() {
        double speed = MathHelper.reverseNormalizeValue(getAnalog(SPEED_SLIDER_CHANNEL), MIN_CYPRESS_VOLTAGE, MAX_CYPRESS_VOLTAGE);
        if (speed < SHOOT_SPEED_DEAD_ZONE) {
            speed = 0;
        } else {
            speed = speed * (1 - MINIMUM_SHOOT_SPEED) + MINIMUM_SHOOT_SPEED;
        }
        return speed;
    }

    private boolean getDigital(int channel) {
        boolean value = false;
        try {
            value = io.getDigital(channel);
        } catch (EnhancedIOException ex) {
            Debug.printException(ex);
        }
        return value;
    }
    
    public boolean getDriveStraight() {
        return rightJoyBtn1.get();
    }
    
    public boolean getDriveSlow() {
        return leftJoyBtn1.get();
    }
    
    public void update() {
        shootTrim = MathHelper.reverseNormalizeValue(getAnalog(TRIM_SLIDER_CHANNEL), MIN_CYPRESS_VOLTAGE, MAX_CYPRESS_VOLTAGE);
        shootTrim = (2 * shootTrim - 1); //always between -1 and 1
        SmartDashboard.putNumber("TrimRPM", getShootTrimRPM());
    }
    
    public double getShootTrimPower() {
        return shootTrim * SHOOT_TRIM_MAX_POWER;
    }
    
    public double getShootTrimRPM() {
        return shootTrim * SHOOT_TRIM_MAX_RPM;
    }

    public Autonomous.StartingPosition getAutonomousStartingPosition() {
        boolean channel1 = getDigital(OI.AUTONOMOUS_SHOOT_SWITCH_CHANNEL_1);
        boolean channel2 = getDigital(OI.AUTONOMOUS_SHOOT_SWITCH_CHANNEL_2);
        
        if (!channel1 && channel2) {
            return Autonomous.StartingPosition.kLeft;
        } else if (channel1 && !channel2) {
            return Autonomous.StartingPosition.kCenter;
        } else if (channel1 && channel2) {
            return Autonomous.StartingPosition.kRight;
        }
        
        return Autonomous.StartingPosition.kNone;
    }

    public Autonomous.TargetPosition getAutonomousTargetPosition() {
        boolean channel1 = getDigital(OI.AUTONOMOUS_DRIVE_SWITCH_CHANNEL_1);
        boolean channel2 = getDigital(OI.AUTONOMOUS_DRIVE_SWITCH_CHANNEL_2);
        
        if (!channel1 && channel2) {
            return Autonomous.TargetPosition.kOutside;
        } else if (channel1 && !channel2) {
            return Autonomous.TargetPosition.kInside;
        } else if (channel1 && channel2) {
            return Autonomous.TargetPosition.kCenter;
        } else if (!channel1 && !channel2) {
            return Autonomous.TargetPosition.kBehind;
        }
        
        // in theory, unreachable, but...
        return Autonomous.TargetPosition.kNone;
    }

    public boolean isFullAutonomous() {
        return !getDigital(FULL_AUTONOMOUS_SWITCH_CHANNEL);
    }

    public boolean autoShootEnabled() {
        return true;
    }

}
