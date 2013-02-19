package org.usfirst.frc948.NRGRobot2013.commands.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc948.NRGRobot2013.commands.Delay;
import org.usfirst.frc948.NRGRobot2013.commands.DriveStraightDistance;
import org.usfirst.frc948.NRGRobot2013.commands.ReleaseFrisbeeCommand;
import org.usfirst.frc948.NRGRobot2013.commands.SetShooterMotorPower;
import org.usfirst.frc948.NRGRobot2013.commands.SetShooterRPM;
import org.usfirst.frc948.NRGRobot2013.commands.TurnCommand;
import org.usfirst.frc948.NRGRobot2013.commands.WaitForMinRPM;
import org.usfirst.frc948.NRGRobot2013.commands.WaitForShooterSpeed;

/**
 *
 * @author irving
 */
public class AutonomousTestGroup extends CommandGroup {

    // WHY ARE NESTED CLASSES SO FUN
    // also why don't we have enums
    public static class Mode {

        private static final int kTimer_val = 0;
        private static final int kEncoder_val = 1;
        private static final int kPID_val = 2;
        
        /**
         * mode: no encoder available, run autonomous on time delay alone
         */
        public static final Mode kTimer = new Mode(Mode.kTimer_val);
        
        /**
         * mode: no PID available, run autonomous on raw motor power and RPM sensor
         */
        public static final Mode kEncoder = new Mode(Mode.kEncoder_val);
        
        /**
         * mode: PID working, run autonomous on PID
         */
        public static final Mode kPID = new Mode(Mode.kPID_val);
        
        public final int mode;

        private Mode(int mode) {
            this.mode = mode;
        }
    }

    public AutonomousTestGroup(Mode mode) {
        if (mode.mode == Mode.kTimer_val) {
            addSequential(new SetShooterMotorPower(1.0));
            addSequential(new Delay(5000));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(3000));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(3000));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new SetShooterMotorPower(0.3));
//            addSequential(new TurnCommand(0.5, -26.5));
//            addSequential(new DriveStraightDistance(-0.5, 10.06));
        } else if (mode.mode == Mode.kEncoder_val) {
            addSequential(new SetShooterMotorPower(1.0));
            addSequential(new WaitForMinRPM(2800));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(400));
            addSequential(new WaitForMinRPM(2800));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(400));
            addSequential(new WaitForMinRPM(2800));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new Delay(400));
            addSequential(new WaitForMinRPM(2800));
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new SetShooterMotorPower(0.0));
//            addSequential(new TurnCommand(0.5, -26.5));
//            addSequential(new DriveStraightDistance(-0.5, 10.06));
        } else if (mode.mode == Mode.kPID_val) {
            addSequential(new SetShooterRPM(3400));
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new WaitForShooterSpeed());
            addSequential(new ReleaseFrisbeeCommand());
            addSequential(new SetShooterMotorPower(0.3));
//            addSequential(new TurnCommand(0.5, -26.5));
//            addSequential(new DriveStraightDistance(-0.5, 10.06));
        }
    }
}
