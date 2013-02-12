/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc948.NRGRobot2013.utilities;

/**
 *
 * @author Sean
 */
public class TargetDistance {
    double highTargetDistance;
    double middleTargetDistance;
    double lowTargetDistance;
    boolean checkHigh;
    boolean checkMiddle;
    boolean checkLow;
        
    public void TargetDistance(double highTargetDist, double middleTargetDist, double lowTargetDist, boolean checkHighT, boolean checkMiddleT, boolean checkLowT){
        highTargetDistance=highTargetDist;
        middleTargetDistance=middleTargetDist;
        lowTargetDistance=lowTargetDist;
        checkHigh=checkHighT;
        checkMiddle=checkMiddleT;
        checkLow=checkLowT;
    }
    
    public double getHighDistance() {
        return highTargetDistance;
    }
    public double getMiddleDistance() {
        return middleTargetDistance;
    }
    public double getLowDistance(){
        return lowTargetDistance;
    }
    public boolean getHighTargetFound(){
        return checkHigh;
    }
    public boolean getMiddleTargetFound(){
        return checkMiddle;
    }
    public boolean getLowTargetFound(){
        return checkLow;
    }
}
