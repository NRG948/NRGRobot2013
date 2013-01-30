/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.frc948.NRGRobot2013.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author freebergm
 * @date 1/29/13
 * @description: Makes the robot wait the specified number of milliseconds.
 */
public class Delay extends Command{

    private long milliseconds;
    private long endTime;
    
    public Delay(long delayMilliseconds) {
        milliseconds = delayMilliseconds;
    }
    
    protected void initialize() {
        endTime = System.currentTimeMillis() + milliseconds;
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
