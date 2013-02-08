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
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc948.NRGRobot2013.commands.*;
import org.usfirst.frc948.NRGRobot2013.subsystems.*;
import org.usfirst.frc948.NRGRobot2013.utilities.LCD;
import org.usfirst.frc948.NRGRobot2013.utilities.MathHelper;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drive drive;
    public static DiscMagazine discMagazine;
    public static IShooter rawShooter;
    public static IShooter PIDShooter;
    public static AimSystem aimSystem;
    public static Climber climber;
    public static Camera camera;
    
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    private final String SHOOTER_TYPE_PID = "PID";

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
	RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drive = new Drive();
        discMagazine = new DiscMagazine();
        //shooter = new PIDShooter();
        rawShooter = new RawShooter();
        PIDShooter = new PIDShooter();
        aimSystem = new AimSystem();
        climber = new Climber();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
	
        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        autonomousCommand = new AutonomousCommand();
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        
        Debug.enable();
        initPreferences();
        
    }
    
    public void disabledInit() {
        
    }
    
    public void disabledPeriodic() {
        LCD.println(LCD.GYRO, 3, "GYRO: " + String.valueOf(Robot.drive.getGyroAngle()));
        LCD.update();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        LCD.update();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        String leftQuad = String.valueOf(MathHelper.round(RobotMap.driveleftQuadrature.getRaw(), 4));
        String rightQuad = String.valueOf(MathHelper.round(RobotMap.driverightQuadrature.getRaw(), 4));
        LCD.println(LCD.DRIVE, 1, "L:" + leftQuad + " R:" + rightQuad);
        LCD.println(LCD.AIM, 2, "MAX:" + (aimSystem.isAtMaxAngle() ? "YES" : "NO") +
                               " MIN:" + (aimSystem.isAtMinAngle() ? "YES" : "NO") + "   ");
        LCD.println(LCD.GYRO, 3, "GYRO: " + String.valueOf(Robot.drive.getGyroAngle()));
        LCD.update();
    }

    /**
     * This function called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    private void initPreferences() {
        Preferences.getInstance().putDouble("TurnP", 0.01);
        Preferences.getInstance().putDouble("TurnI", 0.001);
        Preferences.getInstance().putDouble("TurnD", 0.0);
        Preferences.getInstance().putDouble("GyroSensitivity", RobotMap.DEFAULT_GYRO_SENSITIVITY);
    }
}
