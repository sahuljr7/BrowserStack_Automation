package com.bstack.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * Base Page class that contains common methods and properties
 * All page classes should extend this base class
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    
    // Common timeout constants
    protected static final int DEFAULT_TIMEOUT = 10;
    protected static final int LONG_TIMEOUT = 30;
    protected static final int SHORT_TIMEOUT = 5;
    
    /**
     * Constructor to initialize WebDriver and PageFactory
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
        logger.info("Initialized " + this.getClass().getSimpleName());
    }
    
    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @return WebElement that is now visible
     */
    protected WebElement waitForElementToBeVisible(WebElement element) {
        logger.debug("Waiting for element to be visible: " + element.toString());
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @return WebElement that is now clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable: " + element.toString());
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for element located by By to be visible
     * @param locator By locator
     * @return WebElement that is now visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        logger.debug("Waiting for element to be visible: " + locator.toString());
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Click on element with explicit wait
     * @param element WebElement to click
     */
    protected void clickElement(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            element.click();
            logger.info("Clicked on element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to click on element: " + element.toString(), e);
            throw e;
        }
    }
    
    /**
     * Enter text in input field with explicit wait
     * @param element WebElement input field
     * @param text Text to enter
     */
    protected void enterText(WebElement element, String text) {
        try {
            waitForElementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '" + text + "' in element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to enter text in element: " + element.toString(), e);
            throw e;
        }
    }
    
    /**
     * Get text from element with explicit wait
     * @param element WebElement to get text from
     * @return String text content
     */
    protected String getElementText(WebElement element) {
        try {
            waitForElementToBeVisible(element);
            String text = element.getText();
            logger.info("Retrieved text '" + text + "' from element: " + element.toString());
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + element.toString(), e);
            throw e;
        }
    }
    
    /**
     * Check if element is displayed
     * @param element WebElement to check
     * @return boolean true if displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            boolean isDisplayed = element.isDisplayed();
            logger.debug("Element display status: " + isDisplayed + " for element: " + element.toString());
            return isDisplayed;
        } catch (Exception e) {
            logger.debug("Element not found or not displayed: " + element.toString());
            return false;
        }
    }
    
    /**
     * Get current page title
     * @return String page title
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: " + title);
        return title;
    }
    
    /**
     * Get current page URL
     * @return String current URL
     */
    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current page URL: " + url);
        return url;
    }
    
    /**
     * Navigate to specific URL
     * @param url URL to navigate to
     */
    protected void navigateToUrl(String url) {
        logger.info("Navigating to URL: " + url);
        driver.get(url);
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> 
            ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        logger.debug("Page loaded completely");
    }
    
    /**
     * Scroll element into view
     * @param element WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", element);
        logger.debug("Scrolled to element: " + element.toString());
    }
}