/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc948.NRGRobot2013.utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Greg
 */
public class NRGDigitalIOButton extends Button {

    public static final boolean ACTIVE_STATE_TRUE = true;
    public static final boolean ACTIVE_STATE_FALSE = false;
    
    private final boolean activeState;
    private final int port;

    public NRGDigitalIOButton(int port, boolean activeState) {
        this.port = port;
        this.activeState = activeState;
    }

    public boolean get() {
        try {
            return DriverStation.getInstance().getEnhancedIO().getDigital(port) == activeState;
        } catch (EnhancedIOException ex) {
            return false;
        }
    }
}
