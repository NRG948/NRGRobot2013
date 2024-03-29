package org.usfirst.frc948.NRGRobot2013.utilities;

/**
 * Defines the interface for calculating the ShooterControl parameters.
 * 
 * @author Patrick Lin
 */
public interface ShooterPhysics {
    
    /**
     * 
     * @param distance The distance between the shooter and the target
     * @param platformHeight The height of the robot
     * @param targetHeight The height of the target
     * @return 
     */
    public ShooterControl simuCalculate(double distance, double platformHeight, int goalType); 
    
    /**
     * GoalType = 1 = low goal
     *          = 2 = middle goal 
     *          = 3 = high goal
     */
    public ShooterControl joystickShoot(double distance, double platformHeight, int goalType);
}
