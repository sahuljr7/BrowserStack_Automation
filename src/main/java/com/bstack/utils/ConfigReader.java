package com.bstack.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Configuration Reader utility class for reading properties files
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class ConfigReader {
    
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration properties loaded successfully from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration properties from: {}", CONFIG_FILE_PATH, e);
            // Initialize with default properties if file not found
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties when config file is not available
     */
    private static void setDefaultProperties() {
        logger.info("Setting default configuration properties");
        properties.setProperty("base.url", "https://bstackdemo.com/");
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "10");
        properties.setProperty("page.load.timeout", "30");
        properties.setProperty("default.username", "demouser");
        properties.setProperty("default.password", "testingisfun99");
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found, returning null", key);
        }
        return value;
    }
    
    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        if (value.equals(defaultValue)) {
            logger.debug("Property '{}' not found, using default value: {}", key, defaultValue);
        }
        return value;
    }
    
    /**
     * Get integer property value
     * @param key Property key
     * @param defaultValue Default value if property not found or invalid
     * @return Integer property value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid integer value for property '{}': {}, using default: {}", 
                           key, value, defaultValue);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get boolean property value
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Boolean property value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    // Commonly used configuration getters
    
    /**
     * Get base URL
     * @return Base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://bstackdemo.com/");
    }
    
    /**
     * Get browser name
     * @return Browser name
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Get headless mode setting
     * @return Boolean headless mode
     */
    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
    
    /**
     * Get implicit wait timeout
     * @return Implicit wait timeout in seconds
     */
    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }
    
    /**
     * Get explicit wait timeout
     * @return Explicit wait timeout in seconds
     */
    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 10);
    }
    
    /**
     * Get page load timeout
     * @return Page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }
    
    /**
     * Get default username
     * @return Default username
     */
    public static String getDefaultUsername() {
        return getProperty("default.username", "demouser");
    }
    
    /**
     * Get default password
     * @return Default password
     */
    public static String getDefaultPassword() {
        return getProperty("default.password", "testingisfun99");
    }
    
    /**
     * Get login page URL
     * @return Login page URL
     */
    public static String getLoginUrl() {
        return getBaseUrl() + "signin";
    }
    
    /**
     * Get checkout page URL
     * @return Checkout page URL
     */
    public static String getCheckoutUrl() {
        return getBaseUrl() + "checkout";
    }
    
    /**
     * Reload properties from file
     */
    public static void reloadProperties() {
        logger.info("Reloading configuration properties");
        loadProperties();
    }
    
    /**
     * Print all loaded properties (for debugging)
     */
    public static void printAllProperties() {
        logger.info("=== Configuration Properties ===");
        properties.forEach((key, value) -> logger.info("{} = {}", key, value));
        logger.info("================================");
    }
}