package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;

/**
 * Shoots 4 frisbees (misfire safety) and moves back ~10 feet at ~26 degrees.
 * ...Also known as our autonomous routine.
 * 
 * @author irving
 */
public class Autonomous extends CommandGroup {

    // WHY ARE NESTED CLASSES SO FUN
    // also why don't we have enums
    public static class ShooterMode {

        private static final int kTimer_val = 0;
        private static final int kEncoder_val = 1;
        private static final int kPID_val = 2;
        
        /**
         * mode: no encoder available, run autonomous on time delay alone
         */
        public static final ShooterMode kTimer = new ShooterMode(ShooterMode.kTimer_val);
        
        /**
         * mode: no PID available, run autonomous on raw motor power and RPM sensor
         */
        public static final ShooterMode kEncoder = new ShooterMode(ShooterMode.kEncoder_val);
        
        /**
         * mode: PID working, run autonomous on PID
         */
        public static final ShooterMode kPID = new ShooterMode(ShooterMode.kPID_val);
        
        public final int mode;

        private ShooterMode(int mode) {
            this.mode = mode;
        }
    }

    public static class StartingPosition {
        
        private static final int kLeft_val = 0;
        private static final int kCenter_val = 1;
        private static final int kRight_val = 2;
        
        public static final StartingPosition kLeft = new StartingPosition(kLeft_val);
        public static final StartingPosition kCenter = new StartingPosition(kCenter_val);
        public static final StartingPosition kRight = new StartingPosition(kRight_val);
        
        public final int position;
        
        private StartingPosition(int position) {
            this.position = position;
        }
    }

    public static class TargetPosition {
        
        private static final int kNone_val = 0;
        private static final int kOutside_val = 1;
        private static final int kInside_val = 2;
        
        public static final TargetPosition kNone = new TargetPosition(kNone_val);
        public static final TargetPosition kOutside = new TargetPosition(kOutside_val);
        public static final TargetPosition kInside = new TargetPosition(kInside_val);
        
        public final int position;
        
        private TargetPosition(int position) {
            this.position = position;
        }
    }
    
    // constants for timed autonomous
    private static final int INITIAL_DELAY = 5000;
    private static final int SHOOT_DELAY = 3000;
    
    // constants for min-RPM autonomous
    private static final int MINIMUM_DELAY = 600;
    
    // constants for autonomous driving
    private static final double DEFAULT_SPEED = 0.5;
    
    public Autonomous(ShooterMode mode, StartingPosition start, TargetPosition destination) {
        buildShootSequence(mode);
        buildMoveSequence(start, destination);
        
//        addSequential(new SetDesiredHeading(-26.5));
//        addSequential(new DriveStraightDistance(-0.5, 10));
    }
    
    private void buildShootSequence(ShooterMode mode) {
        if (mode.mode == ShooterMode.kTimer_val) {
            addSequential(new SetShooterMotorPower(1.0));
            addSequential(new Delay(INITIAL_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(SHOOT_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(SHOOT_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
        } else if (mode.mode == ShooterMode.kEncoder_val) {
            addSequential(new SetShooterMotorPower(1.0));
            addSequential(new WaitForMinRPM(Shooter.MIN_RPM_CLOSE_3PT));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(Shooter.MIN_RPM_CLOSE_3PT));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(Shooter.MIN_RPM_CLOSE_3PT));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(Shooter.MIN_RPM_CLOSE_3PT));
            addSequential(new ReleaseFrisbeeCommand());
        } else if (mode.mode == ShooterMode.kPID_val) {
            addSequential(new SetShooterRPM(Shooter.MIN_RPM_CLOSE_3PT));
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
        }
        
        addSequential(new SetShooterMotorPower(0.0));
    }
    
    private void buildMoveSequence(StartingPosition start, TargetPosition destination) {
        if (destination.position == TargetPosition.kNone_val) {
            return;
        }
        
        addSequential(new DriveStraightDistance(-DEFAULT_SPEED, 2));
    }
    
}
