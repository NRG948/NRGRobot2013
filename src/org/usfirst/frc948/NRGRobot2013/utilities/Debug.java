package org.usfirst.frc948.NRGRobot2013.utilities;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;

/**
 *
 * @author Kevin
 */
public class Debug {

    private static boolean print = false;
    private static boolean logging = false;
    
    private static boolean logFileInitialized = false;
    private static boolean logStreamOpen = false;
    
    private static FileConnection file = null;
    private static OutputStream log = null;
    
    private static final String logFilePath = "file:///NRGRobot2013.log";
    
    public static void enableLogging() {
        logging = true;
    }
    
    public static void disableLogging() {
        logging = false;
    }
    
    public static void initLogging() {
        if (logging && !logFileInitialized) {
            file = null;
            
            try {
                file = (FileConnection) Connector.open(logFilePath, Connector.READ_WRITE);
                
                if (file != null) {
                    if (file.exists()) {
                        file.delete();
                        file.create();
                    } else {
                        file.create();
                    }
                    
                    if (file.exists()) {
                        logFileInitialized = true;
                    }
                }
            } catch (IOException ex) {
                printException(ex);
            }
        }
    }
    
    public static void openLoggingStream() {
        if (logging && logFileInitialized && !logStreamOpen) {
            log = null;
            
            try {
                log = file.openOutputStream();
                
                if (log != null) {
                    logStreamOpen = true;
                }
            } catch (IOException ex) {
                printException(ex);
            }
        }
    }
    
    public static void closeLoggingStream() {
        if (logging && logFileInitialized && logStreamOpen) {
            try {
                log.close();
                logStreamOpen = false;
            } catch (IOException ex) {
                printException(ex);
            }
        }
    }
    
//    public static void terminateLogging() {
//        if (logging && logFileInitialized) {
//            if (file != null) {
//                try {
//                    file.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            
//            logFileInitialized = false;
//        }
//    }
    
    public static void enable() {
        print = true;
    }

    public static void disable() {
        print = false;
    }

    public static void printException(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    public static void println(String s) {
        String timeStampString = null;
        
        if (logging && logStreamOpen) {
            timeStampString = String.valueOf(System.currentTimeMillis()) + '|';
            
            try {
                log.write(timeStampString.getBytes());
                log.write(s.getBytes());
                log.write('\n');
            } catch (IOException ex) {
                printException(ex);
            }
        }
        
        if (print) {
            if (timeStampString == null) {
                System.out.print(System.currentTimeMillis());
                System.out.print('|');
            } else {
                System.out.print(timeStampString);
            }
            
            System.out.println(s);
        }
    }
}
