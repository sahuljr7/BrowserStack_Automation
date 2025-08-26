package com.bstack.tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bstack.utils.ConfigReader;
import com.bstack.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

/**
 * Base Test class that contains common setup and teardown methods
 * All test classes should extend this base class
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public abstract class BaseTest {
    
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected ExtentTest test;
    
    // Test configuration
    private String browserName;
    private boolean headless;
    private int implicitWait;
    private int pageLoadTimeout;
    
    /**
     * Setup method that runs before each test suite
     */
    @BeforeSuite
    public void suiteSetup() {
        logger.info("=== Test Suite Started ===");
        ExtentReportManager.initializeReport();
        loadConfiguration();
    }
    
    /**
     * Setup method that runs before each test method
     */
    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test environment");
        initializeDriver();
        configureDriver();
        logger.info("Test environment setup completed");
    }
    
    /**
     * Teardown method that runs after each test method
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser and cleaning up");
            driver.quit();
            logger.info("Browser closed successfully");
        }
    }
    
    /**
     * Teardown method that runs after each test suite
     */
    @AfterSuite
    public void suiteTearDown() {
        ExtentReportManager.flushReport();
        logger.info("=== Test Suite Completed ===");
    }
    
    /**
     * Load configuration from properties file or system properties
     */
    private void loadConfiguration() {
        // Default values
        browserName = System.getProperty("browser", "chrome").toLowerCase();
        headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        implicitWait = Integer.parseInt(System.getProperty("implicit.wait", "10"));
        pageLoadTimeout = Integer.parseInt(System.getProperty("page.load.timeout", "30"));
        
        logger.info("Configuration loaded - Browser: {}, Headless: {}, ImplicitWait: {}s, PageLoadTimeout: {}s", 
                   browserName, headless, implicitWait, pageLoadTimeout);
    }
    
    /**
     * Initialize WebDriver based on browser name
     */
    private void initializeDriver() {
        logger.info("Initializing {} driver", browserName);
        
        try {
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                    
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless");
                    }
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                    
                default:
                    logger.error("Unsupported browser: {}", browserName);
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }
            
            logger.info("{} driver initialized successfully", browserName);
            
        } catch (Exception e) {
            logger.error("Failed to initialize {} driver", browserName, e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    
    /**
     * Configure WebDriver with timeouts and window settings
     */
    private void configureDriver() {
        logger.info("Configuring driver settings");
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        
        // Maximize window if not headless
        if (!headless) {
            driver.manage().window().maximize();
        }
        
        logger.info("Driver configuration completed");
    }
    
    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Take screenshot for test reports
     * @return String base64 encoded screenshot
     */
    protected String takeScreenshot() {
        try {
            return ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
            return "";
        }
    }
    
    /**
     * Log test step
     * @param stepDescription Step description
     */
    protected void logStep(String stepDescription) {
        logger.info("Test Step: {}", stepDescription);
        if (test != null) {
            test.info(stepDescription);
        }
    }
    
    /**
     * Log test result
     * @param result Test result
     */
    protected void logResult(String result) {
        logger.info("Test Result: {}", result);
        if (test != null) {
            test.pass(result);
        }
    }
    
    /**
     * Log test failure
     * @param failure Failure message
     */
    protected void logFailure(String failure) {
        logger.error("Test Failure: {}", failure);
        if (test != null) {
            test.fail(failure);
            // Add screenshot on failure
            String screenshot = takeScreenshot();
            if (!screenshot.isEmpty()) {
                test.addScreenCaptureFromBase64String(screenshot, "Failure Screenshot");
            }
        }
    }
    
    /**
     * Sleep for specified duration (use sparingly, prefer explicit waits)
     * @param milliseconds Duration in milliseconds
     */
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted", e);
        }
    }
}