package com.bstack.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import com.bstack.pages.LoginPage;
import com.bstack.pages.HomePage;
import com.bstack.utils.TestDataProvider;
import com.bstack.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;

/**
 * Test class for Login functionality
 * Contains all test cases related to user authentication
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    private HomePage homePage;
    
    /**
     * Setup method to initialize page objects before each test
     */
    @BeforeMethod
    public void setUpTest() {
        super.setUp();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }
    
    /**
     * Test successful login with valid credentials
     * @param username Valid username from data provider
     */
    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class,
          description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin(String username) {
        test = ExtentReportManager.createTest("Test Successful Login - " + username, 
                                            "Verify user can login successfully with valid credentials", 
                                            "Authentication");
        
        try {
            logStep("Navigate to login page");
            loginPage.navigateToLoginPage();
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
            test.pass("Successfully navigated to login page");
            
            logStep("Perform login with username: " + username);
            homePage = loginPage.login(username);
            
            logStep("Verify successful login");
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
            
            String loggedInUser = homePage.getLoggedInUsername();
            Assert.assertFalse(loggedInUser.isEmpty(), "Logged in username should not be empty");
            
            logResult("Login successful for user: " + username + ", Logged in as: " + loggedInUser);
            test.pass("User successfully logged in as: " + loggedInUser);
            
        } catch (Exception e) {
            logFailure("Login test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test login page navigation and elements visibility
     */
    @Test(description = "Verify login page navigation and UI elements")
    public void testLoginPageNavigation() {
        test = ExtentReportManager.createTest("Test Login Page Navigation", 
                                            "Verify login page loads correctly with all elements visible", 
                                            "UI");
        
        try {
            logStep("Navigate to login page");
            loginPage.navigateToLoginPage();
            
            logStep("Verify login page elements");
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page header should be visible");
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on correct login page URL");
            
            String pageTitle = loginPage.getLoginPageTitle();
            Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
            
            logResult("Login page loaded successfully with title: " + pageTitle);
            test.pass("Login page navigation verified successfully");
            
        } catch (Exception e) {
            logFailure("Login page navigation test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test login with different user types
     */
    @Test(description = "Verify login with different user types")
    public void testLoginWithDifferentUserTypes() {
        test = ExtentReportManager.createTest("Test Login with Different User Types", 
                                            "Verify login works with different predefined user types", 
                                            "Authentication");
        
        String[] userTypes = {"demouser", "fav_user", "existing_orders_user"};
        
        try {
            for (String userType : userTypes) {
                logStep("Testing login with user type: " + userType);
                
                // Navigate to login page
                loginPage.navigateToLoginPage();
                
                // Perform login
                homePage = loginPage.login(userType);
                
                // Verify login
                Assert.assertTrue(homePage.isUserLoggedIn(), 
                                "User should be logged in for user type: " + userType);
                
                String loggedInUser = homePage.getLoggedInUsername();
                test.pass("Successfully logged in as: " + loggedInUser + " for user type: " + userType);
                
                // Logout for next iteration
                loginPage = homePage.logout();
                Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                                "Should return to login page after logout");
                
                logResult("Login/logout cycle completed for user type: " + userType);
            }
            
        } catch (Exception e) {
            logFailure("Multi-user type login test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test logout functionality
     */
    @Test(description = "Verify logout functionality")
    public void testLogout() {
        test = ExtentReportManager.createTest("Test Logout Functionality", 
                                            "Verify user can logout successfully", 
                                            "Authentication");
        
        try {
            logStep("Login with valid credentials");
            loginPage.navigateToLoginPage();
            homePage = loginPage.login("demouser");
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
            
            logStep("Perform logout");
            loginPage = homePage.logout();
            
            logStep("Verify logout");
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should return to login page");
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on correct login page URL");
            
            logResult("Logout completed successfully");
            test.pass("User successfully logged out");
            
        } catch (Exception e) {
            logFailure("Logout test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test login form validation
     */
    @Test(description = "Verify login form validation")
    public void testLoginFormValidation() {
        test = ExtentReportManager.createTest("Test Login Form Validation", 
                                            "Verify login form handles validation correctly", 
                                            "Validation");
        
        try {
            logStep("Navigate to login page");
            loginPage.navigateToLoginPage();
            
            logStep("Test form validation without selecting credentials");
            // Try to click login without selecting username/password
            loginPage.clickLoginButton();
            
            // Should remain on login page
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should remain on login page without valid credentials");
            
            logStep("Test username selection only");
            loginPage.selectUsername("demouser");
            loginPage.clickLoginButton();
            
            // Should still remain on login page without password
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should remain on login page without password");
            
            logResult("Login form validation working correctly");
            test.pass("Form validation verified successfully");
            
        } catch (Exception e) {
            logFailure("Login form validation test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test login page URL accessibility
     */
    @Test(description = "Verify login page URL accessibility")
    public void testLoginPageUrlAccessibility() {
        test = ExtentReportManager.createTest("Test Login Page URL Accessibility", 
                                            "Verify login page is accessible via direct URL", 
                                            "Accessibility");
        
        try {
            logStep("Access login page via direct URL");
            driver.get("https://bstackdemo.com/signin");
            
            logStep("Verify page loads correctly");
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should load correctly");
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on correct login page");
            
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("signin"), "URL should contain 'signin'");
            
            logResult("Login page accessible via direct URL: " + currentUrl);
            test.pass("Login page URL accessibility verified");
            
        } catch (Exception e) {
            logFailure("Login page URL accessibility test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test login session persistence
     */
    @Test(description = "Verify login session persistence")
    public void testLoginSessionPersistence() {
        test = ExtentReportManager.createTest("Test Login Session Persistence", 
                                            "Verify user remains logged in when navigating", 
                                            "Session");
        
        try {
            logStep("Login with valid credentials");
            loginPage.navigateToLoginPage();
            homePage = loginPage.login("demouser");
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
            
            String initialUser = homePage.getLoggedInUsername();
            
            logStep("Navigate to different page and back");
            driver.get("https://bstackdemo.com/");
            
            logStep("Verify user is still logged in");
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should still be logged in");
            
            String persistentUser = homePage.getLoggedInUsername();
            Assert.assertEquals(persistentUser, initialUser, "Username should remain same");
            
            logResult("Login session persisted correctly for user: " + persistentUser);
            test.pass("Session persistence verified successfully");
            
        } catch (Exception e) {
            logFailure("Login session persistence test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
}