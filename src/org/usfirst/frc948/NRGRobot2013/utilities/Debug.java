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
    
    private static boolean loggingInitialized = false;
    
    private static FileConnection file = null;
    private static OutputStream log = null;
    
    public static void initLogging() {
        if (!loggingInitialized) {
            String fileName = "Robot-log-" + System.currentTimeMillis();
            
            file = null;
            log = null;
            
            try {
                file = (FileConnection) Connector.open(fileName, Connector.WRITE);
                
                if (file != null) {
                    log = file.openOutputStream();
                    
                    if (log != null) {
                        loggingInitialized = true;
                    } else {
                        file.close();
                    }
                }
            } catch (IOException ex) {
                printException(ex);
            }
        }
    }
    
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

    public static void print(String s) {
        if (loggingInitialized) {
            try {
                log.write(s.getBytes());
            } catch (IOException ex) {
                printException(ex);
            }
        }
        
        if (print) {
            System.out.print(s);
        }
    }
    
    public static void println(String s) {
        if (loggingInitialized) {
            try {
                log.write(s.getBytes());
                log.write('\n');
            } catch (IOException ex) {
                printException(ex);
            }
        }
        
        if (print) {
            System.out.println(s);
        }
    }
}
