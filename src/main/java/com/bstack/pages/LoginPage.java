package com.bstack.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object Model for BStack Demo Login Page
 * URL: https://bstackdemo.com/signin
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class LoginPage extends BasePage {
    
    // Page URL
    private static final String LOGIN_URL = "https://bstackdemo.com/signin";
    
    // Web Elements using Page Factory annotations
    @FindBy(id = "username")
    private WebElement usernameDropdown;
    
    @FindBy(xpath = "//div[@class='username-dropdown']//div[text()='demouser']")
    private WebElement demouserOption;
    
    @FindBy(xpath = "//div[@class='username-dropdown']//div[text()='fav_user']")
    private WebElement favUserOption;
    
    @FindBy(xpath = "//div[@class='username-dropdown']//div[text()='image_not_loading_user']")
    private WebElement imageNotLoadingUserOption;
    
    @FindBy(xpath = "//div[@class='username-dropdown']//div[text()='existing_orders_user']")
    private WebElement existingOrdersUserOption;
    
    @FindBy(id = "password")
    private WebElement passwordDropdown;
    
    @FindBy(xpath = "//div[@class='password-dropdown']//div[text()='testingisfun99']")
    private WebElement passwordOption;
    
    @FindBy(id = "login-btn")
    private WebElement loginButton;
    
    @FindBy(className = "api-error")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//h3[text()='Login']")
    private WebElement loginPageHeader;
    
    @FindBy(linkText = "Login as a new user")
    private WebElement loginAsNewUserLink;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to login page
     * @return LoginPage instance for method chaining
     */
    public LoginPage navigateToLoginPage() {
        navigateToUrl(LOGIN_URL);
        waitForPageLoad();
        logger.info("Navigated to login page");
        return this;
    }
    
    /**
     * Check if login page is displayed
     * @return boolean true if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        boolean isDisplayed = isElementDisplayed(loginPageHeader);
        logger.info("Login page displayed: " + isDisplayed);
        return isDisplayed;
    }
    
    /**
     * Select username from dropdown
     * @param username Username to select (demouser, fav_user, image_not_loading_user, existing_orders_user)
     * @return LoginPage instance for method chaining
     */
    public LoginPage selectUsername(String username) {
        clickElement(usernameDropdown);
        
        switch (username.toLowerCase()) {
            case "demouser":
                clickElement(demouserOption);
                break;
            case "fav_user":
                clickElement(favUserOption);
                break;
            case "image_not_loading_user":
                clickElement(imageNotLoadingUserOption);
                break;
            case "existing_orders_user":
                clickElement(existingOrdersUserOption);
                break;
            default:
                logger.error("Invalid username provided: " + username);
                throw new IllegalArgumentException("Invalid username: " + username);
        }
        
        logger.info("Selected username: " + username);
        return this;
    }
    
    /**
     * Select password from dropdown
     * @return LoginPage instance for method chaining
     */
    public LoginPage selectPassword() {
        clickElement(passwordDropdown);
        clickElement(passwordOption);
        logger.info("Selected password");
        return this;
    }
    
    /**
     * Click login button
     * @return HomePage instance
     */
    public HomePage clickLoginButton() {
        clickElement(loginButton);
        logger.info("Clicked login button");
        return new HomePage(driver);
    }
    
    /**
     * Perform complete login process
     * @param username Username to login with
     * @return HomePage instance
     */
    public HomePage login(String username) {
        selectUsername(username);
        selectPassword();
        return clickLoginButton();
    }
    
    /**
     * Get error message text if login fails
     * @return String error message
     */
    public String getErrorMessage() {
        if (isElementDisplayed(errorMessage)) {
            return getElementText(errorMessage);
        }
        return "";
    }
    
    /**
     * Check if error message is displayed
     * @return boolean true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    /**
     * Click on "Login as a new user" link
     * @return LoginPage instance
     */
    public LoginPage clickLoginAsNewUser() {
        clickElement(loginAsNewUserLink);
        logger.info("Clicked on 'Login as a new user' link");
        return this;
    }
    
    /**
     * Get login page title
     * @return String page title
     */
    public String getLoginPageTitle() {
        return getPageTitle();
    }
    
    /**
     * Verify if we are on the correct login page
     * @return boolean true if on correct login page
     */
    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("signin") && isLoginPageDisplayed();
    }
}