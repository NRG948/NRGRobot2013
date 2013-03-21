package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import org.usfirst.frc948.NRGRobot2013.commands.SetCameraTilt;

/**
 *
 * @author Sean
 */
public class Camera extends Subsystem {

    public static AxisCamera axisCamera;          // the axis camera object (connected to the switch)
    ColorImage axisImage;
    CriteriaCollection cc;      // the criteria for doing the particle filter operation   // create the criteria for the particle filter
    public static Servo servo = RobotMap.cameraServo;
    public static double servoAngle;
    final static double downAngle = 0d;
    final static double uprightAngle = 30d;
    final double highAspect = 62.0 / 20.0;
    final double middleAspect = 62.0 / 29.0;
    final double lowAspect = 37.0 / 32.0;
    final int TOL = 15;
    final double IMAGEWIDTH = 320.0;
    final double TANGENT = Math.tan((43.5 / 180.0 * Math.PI));

    protected void initDefaultCommand() {
//        this.setDefaultCommand(new SetCameraTilt());
    }

    public Camera() {
//        cc = new CriteriaCollection();      // create the criteria for the particle filter
//        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
//        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
    }

    public void setImage() {
//        try {
//            axisImage = axisCamera.getImage();
//        } catch (NIVisionException ex) {
//            ex.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Get Image Failed" + e);
//        }
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
//                double distance = (5.16667 * (IMAGEWIDTH / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            } else if (checkMiddle && targetNum == 2) {
//                double distance = (5.16667 * (IMAGEWIDTH / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            } else if (checkLow && targetNum == 3) {
//                double distance = (3.08333 * (IMAGEWIDTH / r.boundingRectWidth)) / TANGENT;
//                return (distance);
//            }
//        }
//        return -1; //no target
//    }

    public static void changeServoAngle(double desiredAngle) { //changes the angle of servo to given angle
        servo.setAngle(desiredAngle);
    }

    public static double getServoAngle() {
        servoAngle = servo.getAngle();
        return servoAngle;
    }

    public boolean IsWithinTolerance(double aspect, double checkingRatio, int tolerance) {
        double r = (aspect / checkingRatio) * 100;
        return (r > 100 - tolerance && r < 100 + tolerance);
    }
}
