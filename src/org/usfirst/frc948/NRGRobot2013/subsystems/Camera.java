package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author Sean
 */
public class Camera extends Subsystem {

    public static final double SERVO_SET_SHOOT = 0.69;
    public static final double SERVO_SET_CLIMB = 0.00;
    
    private AxisCamera axisCamera = RobotMap.camera;          // the axis camera object (connected to the switch)
    private ColorImage axisImage;
    
    private final CriteriaCollection cc;      // the criteria for doing the particle filter operation
    
    public static final double FOV = 43.5 / 2.0;
    public static final double TARGET_CENTER = 0.15;
    
    private final static double cosOfTen = 0.985;
    private final double highAspect = (62.0 / 20.0) * cosOfTen;
    private final double middleAspect = 62.0 / 29.0;
    private final double lowAspect = 37.0 / 32.0;
    
    private final int ASPECT_RATIO_TOLERANCE = 15;
    private final double TANGENT = Math.tan((43.5 / 180.0 * Math.PI));

    protected void initDefaultCommand() {
    }

    public Camera() {
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 500, 65536, false);
//        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 20, 400, false);
//        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 12, 400, false);
    }

    public double getNormalizedCenterOfMass() throws NIVisionException {
//        Debug.println("[Camera] getNormalizedCenterOfMass() called");
        long start = System.currentTimeMillis();
        
        try {
            axisImage = axisCamera.getImage();
        } catch (AxisCameraException ex) {
            Debug.println("[Camera] setImage() caught exception (no image available)");
            Debug.printException(ex);
            return TARGET_CENTER;
        } catch (NIVisionException ex) {
            Debug.println("[Camera] setImage() caught exception (creating image)");
            Debug.printException(ex);
            return TARGET_CENTER;
        }
        
        BinaryImage thresholdImage = axisImage.thresholdRGB(0, 100, 200, 255, 200, 255);   // keep only green objects
        BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
        BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
        BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles
        ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
        
//        try {
//            axisImage.write("file:///img/img0.png");
//            thresholdImage.write("file:///img/img1.png");
//            bigObjectsImage.write("file:///img/img2.png");
//            convexHullImage.write("file:///img/img3.png");
//            filteredImage.write("file:///img/img4.png");
//        } catch (NIVisionException ex) {
//            Debug.printException(ex);
//        }
        
        for (int i = 0; i < reports.length; i++) {                                // print results
            ParticleAnalysisReport r = reports[i];
            double aspect = ((double) r.boundingRectWidth / (double) r.boundingRectHeight);
            
            boolean checkHigh = IsWithinTolerance(aspect, highAspect, ASPECT_RATIO_TOLERANCE);
            if (checkHigh /*&& r.boundingRectWidth >= 40*/) {
                NetworkTable targetTable = NetworkTable.getTable("VisionTarget");
                
                targetTable.putBoolean("hasTarget", true);
                targetTable.putNumber("top", r.boundingRectTop);
                targetTable.putNumber("left", r.boundingRectLeft);
                targetTable.putNumber("width", r.boundingRectWidth);
                targetTable.putNumber("height", r.boundingRectHeight);
                
                axisImage.free();
                thresholdImage.free();
                bigObjectsImage.free();
                convexHullImage.free();
                filteredImage.free();
                
                Debug.println("[Camera] getNormalizedCenterOfMass() found CoM_X:" + r.center_mass_x_normalized +
                        " [" + (System.currentTimeMillis() - start) + " ms]");
                
                return r.center_mass_x_normalized;
            }
        }
        
        NetworkTable targetTable = NetworkTable.getTable("VisionTarget");
        targetTable.putBoolean("hasTarget", false);
        
        axisImage.free();
        thresholdImage.free();
        bigObjectsImage.free();
        convexHullImage.free();
        filteredImage.free();
        
        Debug.println("[Camera] getNormalizedCenterOfMass() exiting (no target found) [" +
                        (System.currentTimeMillis() - start) + " ms]");
        
        return TARGET_CENTER;
    }
    
//    public double getDistance(int targetNum, ColorImage image) throws NIVisionException {
//        setImage();
//        BinaryImage thresholdImage = axisImage.thresholdRGB(0, 45, 25, 255, 0, 47);   // keep only green objects
//        BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
//        BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
//        BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles
//        ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
//        for (int i = 0; i < reports.length; i++) {                                // print results
//            ParticleAnalysisReport r = reports[i];
//            double aspect = ((double) r.boundingRectWidth / (double) r.boundingRectHeight);
//            boolean checkHigh = IsWithinTolerance(aspect, highAspect, TOL);
//            boolean checkMiddle = IsWithinTolerance(aspect, middleAspect, TOL);
//            boolean checkLow = IsWithinTolerance(aspect, lowAspect, TOL);
//            if (checkHigh && targetNum == 1) {
//                double distance = (5.16667 * (r.imageWidth / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            } else if (checkMiddle && targetNum == 2) {
//                double distance = (5.16667 * (r.imageWidth / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            } else if (checkLow && targetNum == 3) {
//                double distance = (3.08333 * (r.imageWidth / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            }
//        }
//        return -1; //no target
//    }

    public boolean IsWithinTolerance(double aspect, double checkingRatio, int tolerance) {
        double r = (aspect / checkingRatio) * 100;
        return (r > 100 - tolerance && r < 100 + tolerance);
    }
}
