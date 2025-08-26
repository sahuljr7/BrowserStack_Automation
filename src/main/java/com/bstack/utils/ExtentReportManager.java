package com.bstack.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports Manager utility class for handling test reports
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class ExtentReportManager {
    
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkReporter;
    private static final String REPORTS_FOLDER = "test-output/extent-reports/";
    private static final String REPORT_NAME = "BStackDemo-Test-Report";
    
    /**
     * Initialize ExtentReports
     */
    public static void initializeReport() {
        if (extentReports == null) {
            try {
                // Create reports directory if it doesn't exist
                File reportsDir = new File(REPORTS_FOLDER);
                if (!reportsDir.exists()) {
                    reportsDir.mkdirs();
                    logger.info("Created reports directory: {}", REPORTS_FOLDER);
                }
                
                // Generate timestamped report file name
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String reportPath = REPORTS_FOLDER + REPORT_NAME + "_" + timestamp + ".html";
                
                // Initialize Spark Reporter
                sparkReporter = new ExtentSparkReporter(reportPath);
                configureSparkReporter();
                
                // Initialize ExtentReports
                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);
                setSystemInformation();
                
                logger.info("ExtentReports initialized successfully. Report path: {}", reportPath);
                
            } catch (Exception e) {
                logger.error("Failed to initialize ExtentReports", e);
            }
        }
    }
    
    /**
     * Configure Spark Reporter settings
     */
    private static void configureSparkReporter() {
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("BStack Demo Automation Test Report");
        sparkReporter.config().setReportName("BStack Demo Test Execution Report");
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimelineEnabled(true);
        
        // Custom CSS for better styling
        String customCSS = """
            .navbar-brand {
                color: #fff !important;
            }
            .card-panel {
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .test-node .test-content {
                padding: 15px;
            }
            """;
        sparkReporter.config().setCss(customCSS);
        
        logger.debug("Spark Reporter configured");
    }
    
    /**
     * Set system information in the report
     */
    private static void setSystemInformation() {
        extentReports.setSystemInfo("Application", "BStack Demo");
        extentReports.setSystemInfo("Environment", "Test");
        extentReports.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("User", System.getProperty("user.name"));
        extentReports.setSystemInfo("Execution Date", new Date().toString());
        
        logger.debug("System information set in report");
    }
    
    /**
     * Create a test entry in the report
     * @param testName Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        if (extentReports == null) {
            initializeReport();
        }
        
        ExtentTest test = extentReports.createTest(testName, description);
        logger.debug("Created test entry: {}", testName);
        return test;
    }
    
    /**
     * Create a test entry with category
     * @param testName Test name
     * @param description Test description
     * @param category Test category/tag
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description, String category) {
        ExtentTest test = createTest(testName, description);
        test.assignCategory(category);
        logger.debug("Assigned category '{}' to test: {}", category, testName);
        return test;
    }
    
    /**
     * Flush the report (write to file)
     */
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("ExtentReports flushed successfully");
        }
    }
    
    /**
     * Get ExtentReports instance
     * @return ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            initializeReport();
        }
        return extentReports;
    }
    
    /**
     * Add screenshot to test
     * @param test ExtentTest instance
     * @param screenshotBase64 Base64 encoded screenshot
     * @param title Screenshot title
     */
    public static void addScreenshot(ExtentTest test, String screenshotBase64, String title) {
        if (test != null && screenshotBase64 != null && !screenshotBase64.isEmpty()) {
            test.addScreenCaptureFromBase64String(screenshotBase64, title);
            logger.debug("Added screenshot '{}' to test", title);
        }
    }
    
    /**
     * Log info message to test
     * @param test ExtentTest instance
     * @param message Info message
     */
    public static void logInfo(ExtentTest test, String message) {
        if (test != null) {
            test.info(message);
            logger.debug("Logged info to test: {}", message);
        }
    }
    
    /**
     * Log pass message to test
     * @param test ExtentTest instance
     * @param message Pass message
     */
    public static void logPass(ExtentTest test, String message) {
        if (test != null) {
            test.pass(message);
            logger.debug("Logged pass to test: {}", message);
        }
    }
    
    /**
     * Log fail message to test
     * @param test ExtentTest instance
     * @param message Fail message
     */
    public static void logFail(ExtentTest test, String message) {
        if (test != null) {
            test.fail(message);
            logger.debug("Logged fail to test: {}", message);
        }
    }
    
    /**
     * Log skip message to test
     * @param test ExtentTest instance
     * @param message Skip message
     */
    public static void logSkip(ExtentTest test, String message) {
        if (test != null) {
            test.skip(message);
            logger.debug("Logged skip to test: {}", message);
        }
    }
    
    /**
     * Log warning message to test
     * @param test ExtentTest instance
     * @param message Warning message
     */
    public static void logWarning(ExtentTest test, String message) {
        if (test != null) {
            test.warning(message);
            logger.debug("Logged warning to test: {}", message);
        }
    }
}