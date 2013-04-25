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

    public static class ShooterMode {

        private static final int kTimer_val = 0;
        private static final int kEncoder_val = 1;
        
        // runs autonomous on timer only (failsafe for encoder breaking)
        public static final ShooterMode kTimer = new ShooterMode(ShooterMode.kTimer_val);
        
        // runs autonomous on RPM encoder
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
        private static final int kCenter_val = 3;
        private static final int kBehind_val = 4;
        
        public static final TargetPosition kNone = new TargetPosition(kNone_val);
        public static final TargetPosition kOutside = new TargetPosition(kOutside_val);
        public static final TargetPosition kInside = new TargetPosition(kInside_val);
        public static final TargetPosition kCenter = new TargetPosition(kCenter_val);
        public static final TargetPosition kBehind = new TargetPosition(kBehind_val);
        
        public final int position;
        
        private TargetPosition(int position) {
            this.position = position;
        }
        
        public String toString() {
            switch(position) {
                case kOutside_val: return "R_OUTSIDE";
                case kInside_val: return "R_INSIDE";
                case kCenter_val: return "CENTER";
                case kBehind_val: return "BEHIND";
                default: return "NONE";
            }
        }
    }
    
    // constants for timed autonomous
    private static final int INITIAL_DELAY = 5000;
    private static final int SHOOT_DELAY = 3000;
    
    // constants for min-RPM autonomous
    private static final int MINIMUM_DELAY = 600;
    
    private final ShooterMode mode;
    private final StartingPosition start;
    private final TargetPosition destination;
    
    private final String startingPrefix;
    private final String targetPrefix;
    
    public static class PreferenceKeys {
        public static final String STARTING_PREFIX_LEFT = "AutoL";
        public static final String STARTING_PREFIX_CENTER = "AutoC";
        public static final String STARTING_PREFIX_RIGHT = "AutoR";
        
        public static final String INITIAL_X = "InitialX";
        public static final String INITIAL_Y = "InitialY";
        public static final String INITIAL_TURN = "Turn";
        public static final String INTIAL_DISTANCE = "Distance";
        public static final String SHOOT_TURN = "ShootTurn";
        public static final String SHOOT_RPM = "MinRPM";
        public static final String ALIGN_TURN_LEFT = "PostShootTurnL";
        public static final String ALIGN_TURN_CENTER = "PostShootTurnC";
        public static final String ALIGN_TURN_RIGHT = "PostShootTurnR";
        
        public static final String TARGET_PREFIX_BEHIND = "TargB";
        public static final String TARGET_PREFIX_CENTER = "TargC";
        public static final String TARGET_PREFIX_INSIDE = "TargI";
        public static final String TARGET_PREFIX_OUTSIDE = "TargO";
        
        public static final String INITIAL_DEST_X = "PostShootX";
        public static final String INITIAL_DEST_Y = "PostShootY";
        public static final String AUTO_END_HEADING = "FinalHeading";
        public static final String FINAL_DEST_X = "FinalX";
        public static final String FINAL_DEST_Y = "FinalY";
        
        public static final String[] startingPrefixes = {
            STARTING_PREFIX_LEFT,
            STARTING_PREFIX_CENTER,
            STARTING_PREFIX_RIGHT
        };
        
        public static final String[] startingParameters = {
            INITIAL_X,
            INITIAL_Y,
            INITIAL_TURN,
            INTIAL_DISTANCE,
            SHOOT_TURN,
            SHOOT_RPM,
            ALIGN_TURN_LEFT,
            ALIGN_TURN_CENTER,
            ALIGN_TURN_RIGHT
        };
        
        public static final String[] targetPrefixes = {
            TARGET_PREFIX_BEHIND,
            TARGET_PREFIX_CENTER,
            TARGET_PREFIX_INSIDE,
            TARGET_PREFIX_OUTSIDE
        };
        
        public static final String[] targetParameters = {
            INITIAL_DEST_X,
            INITIAL_DEST_Y,
            AUTO_END_HEADING,
            FINAL_DEST_X,
            FINAL_DEST_Y
        };
        
    }
    
    public static void initPreferences() {
        double[][] startingDefaults = {
            {  7.92, 34.55,   9.0, 2.0,  0.01, 2670.0,   0.0, -50.0, -90.0 },  // left
            { 14.50, 35.83,   0.0, 2.0, -9.00, 2825.0,  45.0,   0.0, -45.0 },  // center
            { 19.08, 34.55, -20.0, 2.0,  0.01, 2670.0, 100.0,  60.0,   0.0 }   // right
        };
        
        double[][] targetDefaults = {
            {  4.0, 29.50, 0.0,  4.5, 8.50  },  // down left side and behind pyramid
            { 13.5, 29.50, 0.0, 13.5, 29.50 },  // go to center and block frisbees
            { 21.0, 29.50, 0.0, 21.5, 6.50  },  // down right side and close to pyramid
            { 23.0, 29.50, 0.0, 23.5, 8.50  }   // down right side and close to wall
        };
        
        for (int i = 0; i < PreferenceKeys.startingPrefixes.length; i++) {
            for (int j = 0; j < PreferenceKeys.startingParameters.length; j++) {
                Preferences.getInstance().putDouble(PreferenceKeys.startingPrefixes[i] + PreferenceKeys.startingParameters[j], startingDefaults[i][j]);
            }
        }
        
        for (int i = 0; i < PreferenceKeys.targetPrefixes.length; i++) {
            for (int j = 0; j < PreferenceKeys.targetParameters.length; j++) {
                Preferences.getInstance().putDouble(PreferenceKeys.targetPrefixes[i] + PreferenceKeys.targetParameters[j], targetDefaults[i][j]);
            }
        }
        
    }
    
    public Autonomous(ShooterMode mode, StartingPosition start, TargetPosition destination) {
        this.mode = mode;
        this.start = start;
        this.destination = destination;
        
        if (start.position == StartingPosition.kLeft_val) {
            startingPrefix = PreferenceKeys.STARTING_PREFIX_LEFT;
        } else if (start.position == StartingPosition.kCenter_val) {
            startingPrefix = PreferenceKeys.STARTING_PREFIX_CENTER;
        } else {
            startingPrefix = PreferenceKeys.STARTING_PREFIX_RIGHT;
        }
        
        if (destination.position == TargetPosition.kBehind_val) {
            targetPrefix = PreferenceKeys.TARGET_PREFIX_BEHIND;
        } else if (destination.position == TargetPosition.kCenter_val) {
            targetPrefix = PreferenceKeys.TARGET_PREFIX_CENTER;
        } else if (destination.position == TargetPosition.kInside_val) {
            targetPrefix = PreferenceKeys.TARGET_PREFIX_INSIDE;
        } else {
            targetPrefix = PreferenceKeys.TARGET_PREFIX_OUTSIDE;
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
        
        addSequential(new SetPositionCommand(Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.INITIAL_X, 0.0),
                    Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.INITIAL_Y, 0.0)));
    }
    
    private void buildShootSequence() {
        if (start.position == StartingPosition.kNone_val) {
            return;
        }
        
        addSequential(new SetShooterMotorPower(1.0));
        
        // If the new autonomous is having problems with the basic shooting and
        // it can't be fixed in time for qualification, just switch to this
        // hard-coded failsafe by uncommenting this next section and commenting
        // out the section after.
        
        /*
        if (start.position == StartingPosition.kRight_val) {
            // RIGHT
            addSequential(new TurnCommand(-20.0, 0.0, 1.0));
            addSequential(new DriveStraightDistance(-0.5, 2.0));
            addSequential(new TurnCommand(0.01, 0.6, 0.6, 1.0));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
        } else if (start.position == StartingPosition.kCenter_val) {
            // CENTER
            addSequential(new DriveStraightDistance(-0.5, 2.0));
            addSequential(new TurnCommand(-9.0, 0.6, 0.6, 1.0));
            addSequential(new WaitForMinRPM(2825.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2825.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2825.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2825.0));
            addSequential(new ReleaseFrisbeeCommand());
        } else if (start.position == StartingPosition.kLeft_val) {
            // LEFT
            addSequential(new TurnCommand(9.0, 1.0, 0.0));
            addSequential(new DriveStraightDistance(-0.5, 2.0));
            addSequential(new TurnCommand(0.01, 0.6, 0.6, 1.0));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(MINIMUM_DELAY));
            addSequential(new WaitForMinRPM(2670.0));
            addSequential(new ReleaseFrisbeeCommand());
        }
        //*/
        
        //*
        double initialTurnAngle = Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.INITIAL_TURN, 0.0);
        
        if (initialTurnAngle != 0) {
            if (start.position == StartingPosition.kLeft_val) {
                addSequential(new TurnCommand(initialTurnAngle, 1.0, 0.0));
            } else if (start.position == StartingPosition.kRight_val) {
                addSequential(new TurnCommand(initialTurnAngle, 0.0, 1.0));
            } else {
                addSequential(new TurnCommand(initialTurnAngle));
            }
        }
        
        addSequential(new DriveStraightDistance(-0.5, Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.INTIAL_DISTANCE, 0.0)));
        
        double shootTurn = Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.SHOOT_TURN, 0.0);
        
        addSequential(new TurnCommand(shootTurn, 0.6, 0.6, 1.0));
        
        double minRPM = Preferences.getInstance().getDouble(startingPrefix + PreferenceKeys.SHOOT_RPM, Shooter.MIN_RPM_CLOSE_3PT);
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
        //*/
        
        addSequential(new SetShooterMotorPower(0.0));
        
        if (destination.position == TargetPosition.kNone_val) {
            return;
        }
        
        String alignTurnKey = PreferenceKeys.ALIGN_TURN_RIGHT;
        if (destination.position == TargetPosition.kBehind_val) {
            alignTurnKey = PreferenceKeys.ALIGN_TURN_LEFT;
        } else if (destination.position == TargetPosition.kCenter_val) {
            alignTurnKey = PreferenceKeys.ALIGN_TURN_CENTER;
        }
        
        double alignTurnAngle = Preferences.getInstance().getDouble(startingPrefix + alignTurnKey, 0.0);
        double alignTurnPower = Math.abs(alignTurnAngle) > 180 ? 0.4 : (Math.abs(alignTurnAngle) > 50.0 ? 0.5 : 0.7);
        addSequential(new TurnCommand(alignTurnAngle, alignTurnPower, alignTurnPower, 5.0));
    }
    
    private void buildMoveSequence() {
        if (destination.position == TargetPosition.kNone_val) {
            return;
        }
        
        addSequential(new DriveToXY(-0.6, Preferences.getInstance().getDouble(targetPrefix + PreferenceKeys.INITIAL_DEST_X, 0.0), Preferences.getInstance().getDouble(targetPrefix + PreferenceKeys.INITIAL_DEST_Y, 0.0)));
        addSequential(new TurnToHeading(Preferences.getInstance().getDouble(targetPrefix + PreferenceKeys.AUTO_END_HEADING, 0.0)));
    }
    
    public CommandGroup buildPostAutonomous() {
        CommandGroup postAutonomous = new CommandGroup() {
            public void initialize() { Debug.println("[PostAutonomous] initialize()"); }
            public void end() { Debug.println("[PostAutonomous] end()"); }
        };
        
        if (destination.position == TargetPosition.kNone_val || destination.position == TargetPosition.kCenter_val) {
            return postAutonomous;
        }
        
        postAutonomous.addSequential(new DriveToXY(-0.75,
                Preferences.getInstance().getDouble(targetPrefix + PreferenceKeys.FINAL_DEST_X, 0.0),
                Preferences.getInstance().getDouble(targetPrefix + PreferenceKeys.FINAL_DEST_Y, 0.0)));
        
        if (destination.position == TargetPosition.kBehind_val) {
            postAutonomous.addSequential(new TurnToHeading(90.0));
            postAutonomous.addSequential(new DriveStraightDistance(0.6, 20.0));
        } else {
            postAutonomous.addSequential(new TurnToHeading(-10.0));
        }
        
//        for (int i = 0; i < 100; i++) {
//            postAutonomous.addSequential(new ShootAtMinRPM(Shooter.MIN_RPM_FAR_3PT));
//        }
        
        return postAutonomous;
    }
    
    public void end() {
        Debug.println("[Autonomous] end()");
    }
    
}
