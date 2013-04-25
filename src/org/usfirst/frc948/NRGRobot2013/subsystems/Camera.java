package org.usfirst.frc948.NRGRobot2013.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc948.NRGRobot2013.RobotMap;
import org.usfirst.frc948.NRGRobot2013.utilities.Debug;

/**
 *
 * @author irvingc
 */
public class Camera extends Subsystem {
    
    private static class GoalType {
        private static final int kOther_val = 0;
        private static final int kMid_val = 1;
        private static final int kHigh_val = 2;
        
        public static final GoalType kOther = new GoalType(kOther_val);
        public static final GoalType kMid = new GoalType(kMid_val);
        public static final GoalType kHigh = new GoalType(kHigh_val);
        
        public final int type;
        
        private GoalType(int position) {
            this.type = position;
        }
        
        public String toString() {
            switch (type) {
                case kOther_val: return "OTHER";
                case kMid_val: return "MID";
                case kHigh_val: return "HIGH";
                default: return "???";
            }
        }
    }

    private final AxisCamera axisCamera = RobotMap.camera;
    
    private final CriteriaCollection cc = initParticleFilter();
    
    public static final double FOV = 67.0 / 2.0;
    public static final double TARGET_CENTER = 0.15;
    
    private final double ASPECT_RATIO_HIGH = 62.0 / 20.0; // 3.10
    private final double ASPECT_RATIO_MID = 62.0 / 29.0;  // 2.14
    private final double ASPECT_RATIO_LOW = 37.0 / 32.0;  // 1.17
    
    private final double ASPECT_RATIO_TOLERANCE = 0.20;
    
    private final double FOV_TANGENT = Math.tan(FOV / 180.0 * Math.PI) * 2;
    
    private boolean updated = false;
    private boolean analyzed = false;
    private boolean tableWritten = true;
    
    private boolean writeImageFilesOnUpdate = false;
    private int imageSet = 0;
    
    private ParticleAnalysisReport[] reports;
    private GoalType[] reportType;
    
    private static CriteriaCollection initParticleFilter() {
        CriteriaCollection cc = new CriteriaCollection();
        
        // particle area greater than 270
        cc.addCriteria(MeasurementType.IMAQ_MT_AREA, 270, 65536, false);
        
        return cc;
    }
    
    protected void initDefaultCommand() {
    }
    
    public boolean getImageWrite() {
        return writeImageFilesOnUpdate;
    }

    public void setImageWrite(boolean set) {
        writeImageFilesOnUpdate = set;
    }
    
    // fetch image from camera and run it through filters to generate set of
    //   particle reports
    public void analyzeImage() {
        long time = System.currentTimeMillis();
        
        updated = false;
        analyzed = false;
        
        // fetch image from camera
        ColorImage axisImage;
        try {
            axisImage = axisCamera.getImage();
        } catch (AxisCameraException ex) {
            Debug.println("[Camera] analyzeImage() caught exception (no image available)");
            Debug.printException(ex);
            return;
        } catch (NIVisionException ex) {
            Debug.println("[Camera] analyzeImage() caught exception (fetching image)");
            Debug.printException(ex);
            return;
        }
        
        // intermediate images
        BinaryImage thresholdImage;
        BinaryImage convexHullImage;
        BinaryImage filteredImage;
        
        try {
            // keep only green objects
            thresholdImage = axisImage.thresholdRGB(0, 20, 75, 255, 65, 150);
            
            // fill in polygons
            convexHullImage = thresholdImage.convexHull(false);
            
            // filter particles
            filteredImage = convexHullImage.particleFilter(cc);
            
        } catch (NIVisionException ex) {
            Debug.println("[Camera] analyzeImage() caught exception (filtering image)");
            Debug.printException(ex);
            return;
        }
        
        if (writeImageFilesOnUpdate) {
            try {
                String prefix = "/img/img_set" + imageSet + "_";
                String extension = ".png";
                axisImage.write(prefix + "0" + extension);
                thresholdImage.write(prefix + "1" + extension);
                convexHullImage.write(prefix + "2" + extension);
                filteredImage.write(prefix + "3" + extension);
                imageSet++;
            } catch (NIVisionException ex) {
                Debug.println("[Camera] analyzeImage() caught exception (write image files)");
                Debug.printException(ex);
            }
        }
        
        try {
            // analyze image for particles
            reports = filteredImage.getOrderedParticleAnalysisReports();
            Debug.println("[Camera] analyzeImage() found " + reports.length + " particles");
        } catch (NIVisionException ex) {
            Debug.println("[Camera] analyzeImage() caught exception (particle analysis)");
            Debug.printException(ex);
            return;
        }

        try {
            // release memory manually
            axisImage.free();
            thresholdImage.free();
            convexHullImage.free();
            filteredImage.free();
        } catch (NIVisionException ex) {
            Debug.println("[Camera] analyzeImage() caught exception (free image files)");
            Debug.printException(ex);
        }
        
        updated = true;
        
        time = System.currentTimeMillis() - time;
        Debug.println("[Camera] analyzeImage() exiting properly [" + time + " ms]");
    }
    
    // analyze last set of particle reports, looking for goals by aspect ratio
    public void analyzeReports() {
        reportType = new GoalType[reports.length];

//        // analyze aspect ratios
//        for (int i = 0; i < reports.length; i++) {
//            ParticleAnalysisReport report = reports[i];
//            
//            double aspectRatio = ((double) report.boundingRectWidth / (double) report.boundingRectHeight);
//            
//            boolean isHigh = isWithinTolerance(aspectRatio, highAspect, ASPECT_RATIO_TOLERANCE);
//            boolean isMid = isWithinTolerance(aspectRatio, middleAspect, ASPECT_RATIO_TOLERANCE);
//            
//            if (isHigh) {
//                reportType[i] = GoalType.kHigh;
//            } else if (isMid) {
//                reportType[i] = GoalType.kMid;
//            } else {
//                reportType[i] = GoalType.kOther;
//            }
//            
//            Debug.println("[Camera] analyzeReports() - Report " + i);
//            Debug.println("[Camera]     Dimensions .... : " + report.boundingRectWidth + " x " + report.boundingRectHeight);
//            Debug.println("[Camera]     Area .......... : " + report.particleArea);
//            Debug.println("[Camera]     Aspect ratio... : " + aspectRatio);
//            Debug.println("[Camera]     Type .......... : " + reportType[i].toString());
//        }
        
        // take report with the widest aspect ratio and mark it as the high goal
        int bestIndex = 0;
        double bestRatio = 0;
        for (int i = 0; i < reports.length; i++) {
            ParticleAnalysisReport report = reports[i];
            
            double aspectRatio = ((double) report.boundingRectWidth / (double) report.boundingRectHeight);

            // check if aspect ratio vaguely resembles a goal
            if (aspectRatio >= ASPECT_RATIO_MID * (1.0 - ASPECT_RATIO_TOLERANCE) && aspectRatio <= ASPECT_RATIO_HIGH * (1.0 + ASPECT_RATIO_TOLERANCE)) {
                if (aspectRatio > bestRatio) {
                    reportType[bestIndex] = GoalType.kMid;
                    reportType[i] = GoalType.kHigh;
                    bestIndex = i;
                    bestRatio = aspectRatio;
                } else {
                    reportType[i] = GoalType.kMid;
                }
            } else {
                reportType[i] = GoalType.kOther;
            }
            
            Debug.println("[Camera] analyzeReports() - Report " + i);
            Debug.println("[Camera]     Dimensions .... : " + report.boundingRectWidth + " x " + report.boundingRectHeight);
            Debug.println("[Camera]     Area .......... : " + report.particleArea);
            Debug.println("[Camera]     Aspect ratio... : " + aspectRatio);
            Debug.println("[Camera]     Type .......... : " + reportType[i].toString());
        }
        
        analyzed = true;
        tableWritten = false;
    }
    
    // red
    private static final int BOX_HIGH_R = 255;
    private static final int BOX_HIGH_G = 0;
    private static final int BOX_HIGH_B = 0;
    
    // yellow
    private static final int BOX_MID_R = 255;
    private static final int BOX_MID_G = 255;
    private static final int BOX_MID_B = 0;
    
    // magenta
    private static final int OTHER_R = 255;
    private static final int OTHER_G = 0;
    private static final int OTHER_B = 255;
    
    public void writeToVisionTable() {
        writeTable(NetworkTable.getTable("VisionTarget"));
    }
    
    private void writeTable(NetworkTable table) {
        if (table == null) {
            System.out.println("[Camera] writeTable() passed null table");
        } else {
            if (updated && !tableWritten) {
                for (int i = 0; i < reports.length; i++) {
                    String prefix = "box" + i;
                    
                    int R, G, B;
                    
                    if (reportType[i].type == GoalType.kHigh_val) {
                        R = BOX_HIGH_R;
                        G = BOX_HIGH_G;
                        B = BOX_HIGH_B;
                    } else if (reportType[i].type == GoalType.kMid_val) {
                        R = BOX_MID_R;
                        G = BOX_MID_G;
                        B = BOX_MID_B;
                    } else {
                        R = OTHER_R;
                        G = OTHER_G;
                        B = OTHER_B;
                    }
                    
                    table.putNumber(prefix + "lineR", R);
                    table.putNumber(prefix + "lineG", G);
                    table.putNumber(prefix + "lineB", B);
                    
                    table.putNumber(prefix + "top", reports[i].boundingRectTop);
                    table.putNumber(prefix + "left", reports[i].boundingRectLeft);
                    table.putNumber(prefix + "width", reports[i].boundingRectWidth);
                    table.putNumber(prefix + "height", reports[i].boundingRectHeight);
                    
                    table.putNumber(prefix + "cmx", reports[i].center_mass_x);
                }
                
                table.putNumber("targetsFound", reports.length);
                table.putBoolean("updated", true);
            } else {
                table.putBoolean("updated", false);
            }
            
            tableWritten = true;
        }
    }
    
    private static boolean isWithinTolerance(double aspect, double checkingRatio, double tolerance) {
        double r = aspect / checkingRatio;
        return (r > 1.0 - tolerance && r < 1.0 + tolerance);
    }
    
    public double getNormalizedCenterOfMass() {
        if (analyzed) {
            for (int i = 0; i < reports.length; i++) {
                if (reportType[i].type == GoalType.kHigh_val) {
                    return reports[i].center_mass_x_normalized;
                }
            }
        }
        
        return TARGET_CENTER;
    }
    
    public double calculateNormalizedCenterOfMass() {
        analyzeImage();
        if (!updated) { return TARGET_CENTER; }
        
        analyzeReports();
        if (!analyzed) { return TARGET_CENTER; }
        
        try {
            writeToVisionTable();
        } catch (Exception ex) {
            Debug.printException(ex);
        }

        return getNormalizedCenterOfMass();
    }
    
}
