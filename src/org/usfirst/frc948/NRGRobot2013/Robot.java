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
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import org.usfirst.frc948.NRGRobot2013.subsystems.*;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;
import org.usfirst.frc948.NRGRobot2013.utilities.PositionTracker;
import org.usfirst.frc948.NRGRobot2013.utilities.PreferenceKeys;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand = null;

    public static IOperatorInterface oi;
    public static final boolean USE_NEW_OI = false;
    public static PositionTracker positionTracker;
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drive drive;
    public static DiscMagazine discMagazine;
    public static Shooter shooter;
    public static Climber climber;
    public static Camera camera;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Debug.println("Far over...");

        RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drive = new Drive();
        discMagazine = new DiscMagazine();
        shooter = new Shooter();
        climber = new Climber();
        camera = new Camera();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        if (USE_NEW_OI) {
            oi = new OI2();
        }
        else {
            oi = new OI();
        }
        
        positionTracker = new PositionTracker(RobotMap.drivegyro, RobotMap.driveleftQuadrature, RobotMap.driverightQuadrature);
        
        Debug.enable();
//        initPreferences();

        RobotMap.compressor.start();

        Debug.println("   ...the misty mountains colddd.");
    }

    public void disabledInit() {
        
    }

    public void disabledPeriodic() {
        periodicAll();
    }

    public void autonomousInit() {
        RobotMap.drivegyro.reset();
        
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
            while (autonomousCommand.isRunning()) {}  // wait for cancel to finish
        }
        
        Autonomous.StartingPosition startingPosition = oi.getAutonomousStartingPosition();
        Autonomous.TargetPosition targetPosition = oi.getAutonomousTargetPosition();

        if (startingPosition != null) {
            autonomousCommand = new Autonomous(Autonomous.ShooterMode.kEncoder, startingPosition, targetPosition);
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        positionTracker.update();
        periodicAll();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        positionTracker.update();      
        periodicAll();
    }

    /**
     * This function called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        periodicAll();
    }

    public static void initPreferences() {
        Preferences.getInstance().putDouble(PreferenceKeys.TURN_P, TurnCommand.kDefaultP);
        Preferences.getInstance().putDouble(PreferenceKeys.TURN_I, TurnCommand.kDefaultI);
        Preferences.getInstance().putDouble(PreferenceKeys.TURN_D, TurnCommand.kDefaultD);
        Preferences.getInstance().putDouble(PreferenceKeys.GYRO_SENSITIVITY, RobotMap.DEFAULT_GYRO_SENSITIVITY);
        Preferences.getInstance().putDouble(PreferenceKeys.MAX_ACCEL, Drive.DEFAULT_MAX_ACCEL);
        Preferences.getInstance().putDouble(PreferenceKeys.OVER_REV_FACTOR, Shooter.DEFAULT_OVER_REV);
        Preferences.getInstance().putDouble(PreferenceKeys.SHOOT_DELAY, ReleaseFrisbeeCommand.DELAY);
        Preferences.getInstance().putDouble(PreferenceKeys.CLIMBER_POWER, Climber.DEFAULT_POWER);
        Preferences.getInstance().putBoolean(PreferenceKeys.SHOOTER_USE_PID, Shooter.DEFAULT_USE_PID);
        Preferences.getInstance().putDouble(PreferenceKeys.SHOOTER_P, Shooter.kDefaultP);
        Preferences.getInstance().putDouble(PreferenceKeys.SHOOTER_I, Shooter.kDefaultI);
        Preferences.getInstance().putDouble(PreferenceKeys.SHOOTER_D, Shooter.kDefaultD);
        Preferences.getInstance().putDouble(PreferenceKeys.SHOOT_PID_SCALE_FACTOR, Shooter.PID_OUTPUT_SCALE_VALUE);
        Preferences.getInstance().putDouble(PreferenceKeys.DOUBLE_PARAM1, 0.0);
        Preferences.getInstance().putDouble(PreferenceKeys.DOUBLE_PARAM2, 0.0);
        Preferences.getInstance().putDouble(PreferenceKeys.DOUBLE_PARAM3, 0.0);
        Preferences.getInstance().putDouble(PreferenceKeys.DRIVE_P, Drive.kDefaultP);
        Preferences.getInstance().putDouble(PreferenceKeys.DRIVE_I, Drive.kDefaultI);
        Preferences.getInstance().putDouble(PreferenceKeys.DRIVE_D, Drive.kDefaultD);
    }

    /*
     * Method to be called in all periodic loops.
     * Good for things like LCD prints or general updates.
     */
    private void periodicAll() {
        String leftQuad = String.valueOf(MathHelper.round(RobotMap.driveleftQuadrature.getRaw(), 4));
        String rightQuad = String.valueOf(MathHelper.round(RobotMap.driverightQuadrature.getRaw(), 4));

        String gyro = String.valueOf(MathHelper.round(RobotMap.drivegyro.getAngle(), 1));
        String target = String.valueOf(MathHelper.round(Robot.drive.getDesiredHeading(), 1));

        RobotMap.shooterQuadrature.update();
        oi.update();

        LCD.clearLine(3);
        LCD.println(LCD.DRIVE, 1, "L:" + leftQuad + " R:" + rightQuad);
        LCD.println(LCD.GYRO, 2, "GYRO:" + gyro + " TARG:" + target);
//        LCD.println(LCD.SHOOT, 3, "RELEASED: " + Robot.discMagazine.getCount());
        LCD.println(true, 3, "AVG RPM:" + RobotMap.shooterQuadrature.averageRPM());
        LCD.println(true, 4, "SHOOT PID:" + oi.shooterUsePID() + "  ");
        LCD.println(true, 5, "AUTO START:" + oi.getAutonomousStartingPosition().position + " TGT:"+oi.getAutonomousTargetPosition().position);
        LCD.update();

        // Show what command each subsystem is executing
        SmartDashboard.putData("Drive Subsystem", drive);
        SmartDashboard.putData("Shooter Subsystem", shooter);
        SmartDashboard.putData("DiscMag Subsystem", discMagazine);
        SmartDashboard.putData("Climber Subsystem", climber);
        SmartDashboard.putData("Camera Subsystem", camera);
        
        SmartDashboard.putNumber("shooter RPM", RobotMap.shooterQuadrature.getRPM());
        SmartDashboard.putNumber("average RPM", RobotMap.shooterQuadrature.averageRPM());
        Robot.discMagazine.getVoltage();
    }

}
