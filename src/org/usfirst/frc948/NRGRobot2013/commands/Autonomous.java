package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.Robot;
import org.usfirst.frc948.NRGRobot2013.subsystems.Shooter;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 * 
 * @author irving
 */
public class Autonomous extends CommandGroup {

    // WHY ARE NESTED CLASSES SO FUN
    // also why don't we have enums
    public static class ShooterMode {

        private static final int kTimer_val = 0;
        private static final int kEncoder_val = 1;
        
        /**
         * mode: no encoder available, run autonomous on time delay alone
         */
        public static final ShooterMode kTimer = new ShooterMode(ShooterMode.kTimer_val);
        
        /**
         * mode: no PID available, run autonomous on raw motor power and RPM sensor
         */
        public static final ShooterMode kEncoder = new ShooterMode(ShooterMode.kEncoder_val);
        
        public final int mode;

        private ShooterMode(int mode) {
            this.mode = mode;
        }
    }

    public static class StartingPosition {
        
        private static final int kNone_val = 0;
        private static final int kLeft_val = 1;
        private static final int kCenter_val = 2;
        private static final int kRight_val = 3;
        
        public static final StartingPosition kNone = new StartingPosition(kNone_val);
        public static final StartingPosition kLeft = new StartingPosition(kLeft_val);
        public static final StartingPosition kCenter = new StartingPosition(kCenter_val);
        public static final StartingPosition kRight = new StartingPosition(kRight_val);
        
        public final int position;
        
        private StartingPosition(int position) {
            this.position = position;
        }
        
        public String toString() {
            switch (position) {
                case kLeft_val: return "LEFT";
                case kCenter_val: return "CENTER";
                case kRight_val: return "RIGHT";
                default: return "NONE";
            }
        }
    }

    public static class TargetPosition {
        
        private static final int kNone_val = 0;
        private static final int kOutside_val = 1;
        private static final int kInside_val = 2;
        private static final int kLeft_val = 3;
        
        public static final TargetPosition kNone = new TargetPosition(kNone_val);
        public static final TargetPosition kOutside = new TargetPosition(kOutside_val);
        public static final TargetPosition kInside = new TargetPosition(kInside_val);
        public static final TargetPosition kLeft = new TargetPosition(kLeft_val);
        
        public final int position;
        
        private TargetPosition(int position) {
            this.position = position;
        }
        
        public String toString() {
            switch(position) {
                case kOutside_val: return "OUTSIDE";
                case kInside_val: return "CENTER";
                case kLeft_val: return "LEFT";
                default: return "NONE";
            }
        }
    }
    
    // constants for timed autonomous
    private static final int INITIAL_DELAY = 5000;
    private static final int SHOOT_DELAY = 3000;
    
    // constants for min-RPM autonomous
    private static final int MINIMUM_DELAY = 600;
    
    // constants for autonomous driving
    private static final double DEFAULT_TURN_SPEED = 0.3;
    private static final double DEFAULT_SPEED = 0.5;
    
    private final ShooterMode mode;
    private final StartingPosition start;
    private final TargetPosition destination;
    
    private final String prefix;
    
    public static class PreferenceKeys {
        public static final String PREFIX_LEFT = "AutoL";
        public static final String PREFIX_CENTER = "AutoC";
        public static final String PREFIX_RIGHT = "AutoR";
        
        public static final String INITIAL_X = "InitialX";
        public static final String INITIAL_Y = "InitialY";
        public static final String INITIAL_TURN = "Turn";
        public static final String INTIAL_DISTANCE = "Distance";
        public static final String SHOOT_RPM = "MinRPM";
        public static final String ALIGN_TURN = "PostShootTurn";
        public static final String DEST_X = "PostShootX";
        public static final String DEST_Y = "PostShootY";
        public static final String FINAL_TURN = "FinalTurn";
        public static final String OUTSIDE_X = "FinalPos1X";
        public static final String OUTSIDE_Y = "FinalPos1Y";
        public static final String INSIDE_X = "FinalPos2X";
        public static final String INSIDE_Y = "FinalPos2Y";
        public static final String SHOOT_TURN = "ShootTurn";
        
        public static final String[] prefixes = {
            PREFIX_LEFT,
            PREFIX_CENTER,
            PREFIX_RIGHT
        };
        
        public static final String[] array = {
            INITIAL_X,
            INITIAL_Y,
            INITIAL_TURN,
            INTIAL_DISTANCE,
            SHOOT_RPM,
            ALIGN_TURN,
            DEST_X,
            DEST_Y,
            FINAL_TURN,
            OUTSIDE_X,
            OUTSIDE_Y,
            INSIDE_X,
            INSIDE_Y,
            SHOOT_TURN
        };
    }
    
    public static void initPreferences() {
        double[][] defaults = {
            {  7.92, 34.83,   9.0, 2.0, 2700.0, -30.0, 24.0, 29.00, 0.00, 25.2, 7.75, 24.0, 5.5,  0.01 },  // left
            { 14.50, 35.83,   0.0, 2.0, 2825.0,   0.0, 24.0, 29.00, 0.00, 25.2, 7.75, 24.0, 5.5, -9.00 },  // center
            { 19.08, 34.83, -20.0, 2.0, 2700.0,   0.0, 24.0, 29.00, 0.00, 25.2, 7.75, 24.0, 5.5,  0.01 }   // right
        };
        
        String[] keys = PreferenceKeys.array;
        String[] prefixes = PreferenceKeys.prefixes;
        
        for (int i = 0; i < prefixes.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                Preferences.getInstance().putDouble(prefixes[i] + keys[j], defaults[i][j]);
            }
        }
    }
    
    public Autonomous(ShooterMode mode, StartingPosition start, TargetPosition destination) {
        this.mode = mode;
        this.start = start;
        this.destination = destination;
        
        if (start.position == StartingPosition.kLeft_val) {
            prefix = PreferenceKeys.PREFIX_LEFT;
        } else if (start.position == StartingPosition.kRight_val) {
            prefix = PreferenceKeys.PREFIX_RIGHT;
        } else {
            prefix = PreferenceKeys.PREFIX_CENTER;
        }
        
        Debug.println("[Autonomous] building sequence " + mode + " " + start + " " + destination);
        
        initializePosition();
        buildShootSequence();
        buildMoveSequence();
    }
    
    private void initializePosition() {
        addSequential(new ResetSensorsCommand());
        
        addSequential(new Command() {
            protected void initialize() {}
            protected void execute() {Robot.positionTracker.init();}
            protected boolean isFinished() {return true;}
            protected void end() {}
            protected void interrupted() {}
        });
        
        addSequential(new SetPositionCommand(Preferences.getInstance().getDouble(prefix + PreferenceKeys.INITIAL_X, 0.0),
                    Preferences.getInstance().getDouble(prefix + PreferenceKeys.INITIAL_Y, 0.0)));
    }
    
    private void buildShootSequence() {
        if (start.position == StartingPosition.kNone_val) {
            return;
        }
        
        addSequential(new SetShooterMotorPower(1.0));
        
        double initialTurnAngle = Preferences.getInstance().getDouble(prefix + PreferenceKeys.INITIAL_TURN, 0.0);
        
        if (initialTurnAngle != 0) {
            if (start.position == StartingPosition.kLeft_val) {
                addSequential(new TurnCommand(initialTurnAngle, 1.0, 0.0));
            } else if (start.position == StartingPosition.kRight_val) {
                addSequential(new TurnCommand(initialTurnAngle, 0.0, 1.0));
            } else {
                addSequential(new TurnCommand(initialTurnAngle));
            }
        }
        
        addSequential(new DriveStraightDistance(-DEFAULT_SPEED, Preferences.getInstance().getDouble(prefix + PreferenceKeys.INTIAL_DISTANCE, 0.0)));
        
        double shootTurn = Preferences.getInstance().getDouble(prefix + PreferenceKeys.SHOOT_TURN, 0.0);
        
        addSequential(new TurnCommand(shootTurn, 0.6, 0.6, 1.0));
        
        double minRPM = Preferences.getInstance().getDouble(prefix + PreferenceKeys.SHOOT_RPM, Shooter.MIN_RPM_CLOSE_3PT);
        if (mode.mode == ShooterMode.kTimer_val) {
            addSequential(new Delay(INITIAL_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(SHOOT_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(SHOOT_DELAY));
            addSequential(new ReleaseFrisbeeCommand());
        } else if (mode.mode == ShooterMode.kEncoder_val) {
            addSequential(new WaitForMinRPM(minRPM));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(minRPM));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(minRPM));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(minRPM));
            addSequential(new ReleaseFrisbeeCommand());
        }
        
        addSequential(new SetShooterMotorPower(0.0));
        addSequential(new TurnCommand(Preferences.getInstance().getDouble(prefix + PreferenceKeys.ALIGN_TURN, 0.0), DEFAULT_TURN_SPEED));
    }
    
    private void buildMoveSequence() {
        if (destination.position == TargetPosition.kNone_val) {
            return;
        }
        
        addSequential(new DriveToXY(-0.6, Preferences.getInstance().getDouble(prefix + PreferenceKeys.DEST_X, 0.0), Preferences.getInstance().getDouble(prefix + PreferenceKeys.DEST_Y, 0.0)));
        addSequential(new TurnToHeading(0));
    }
    
    public CommandGroup buildPostAutonomous() {
        CommandGroup postAutonomous = new CommandGroup() {
            public void initialize() {
                Debug.println("[PostAutonomous] initialize()");
            }
            
            public void end() {
                Debug.println("[PostAutonomous] end()");
            }
        };
        
        if (destination.position == TargetPosition.kNone_val) {
            return postAutonomous;
        }
        
        // rough turn so that DriveToXY doesn't drive in wide arc
        postAutonomous.addSequential(new TurnToHeading(0.0, 0.6, 0.6, 10.0));
        
        if (destination.position == TargetPosition.kOutside_val) {
            postAutonomous.addSequential(new DriveToXY(-0.7,
                    Preferences.getInstance().getDouble(prefix + PreferenceKeys.OUTSIDE_X, 0.0),
                    Preferences.getInstance().getDouble(prefix + PreferenceKeys.OUTSIDE_Y, 0.0)));
        } else if (destination.position == TargetPosition.kInside_val) {
            postAutonomous.addSequential(new DriveToXY(-0.7,
                    Preferences.getInstance().getDouble(prefix + PreferenceKeys.INSIDE_X, 0.0),
                    Preferences.getInstance().getDouble(prefix + PreferenceKeys.INSIDE_Y, 0.0)));
        }
        
        postAutonomous.addSequential(new TurnToHeading(-10.0));
        
//        for (int i = 0; i < 100; i++) {
//            postAutonomous.addSequential(new ShootAtMinRPM(Shooter.MIN_RPM_FAR_3PT));
//        }
        
        return postAutonomous;
    }
    
    public void end() {
        Debug.println("[Autonomous] end()");
    }
    
}
