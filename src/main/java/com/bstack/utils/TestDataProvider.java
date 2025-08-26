package com.bstack.utils;

import org.testng.annotations.DataProvider;

/**
 * Test Data Provider class for supplying test data to test methods
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class TestDataProvider {
    
    /**
     * Data provider for login test scenarios
     * @return Object[][] array containing username data
     */
    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return new Object[][] {
            {"demouser"},
            {"fav_user"},
            {"existing_orders_user"}
        };
    }
    
    /**
     * Data provider for invalid login scenarios
     * @return Object[][] array containing invalid username data
     */
    @DataProvider(name = "invalidLoginData")
    public static Object[][] getInvalidLoginData() {
        return new Object[][] {
            {"invalid_user"},
            {""},
            {"test_user"}
        };
    }
    
    /**
     * Data provider for checkout form data
     * @return Object[][] array containing checkout form data
     */
    @DataProvider(name = "checkoutData")
    public static Object[][] getCheckoutData() {
        return new Object[][] {
            {"John", "Doe", "123 Main St", "California", "90210"},
            {"Jane", "Smith", "456 Oak Ave", "New York", "10001"},
            {"Mike", "Johnson", "789 Pine Rd", "Texas", "75001"}
        };
    }
    
    /**
     * Data provider for product filter scenarios
     * @return Object[][] array containing filter names
     */
    @DataProvider(name = "productFilters")
    public static Object[][] getProductFilters() {
        return new Object[][] {
            {"Apple"},
            {"Samsung"},
            {"OnePlus"},
            {"Google"}
        };
    }
    
    /**
     * Data provider for search scenarios
     * @return Object[][] array containing search terms
     */
    @DataProvider(name = "searchData")
    public static Object[][] getSearchData() {
        return new Object[][] {
            {"iPhone"},
            {"Samsung Galaxy"},
            {"OnePlus"},
            {"Pixel"}
        };
    }
    
    /**
     * Data provider for browser testing
     * @return Object[][] array containing browser names
     */
    @DataProvider(name = "browsers")
    public static Object[][] getBrowsers() {
        return new Object[][] {
            {"chrome"},
            {"firefox"},
            {"edge"}
        };
    }
    
    /**
     * Data provider for user profile data
     * @return Object[][] array containing user profile information
     */
    @DataProvider(name = "userProfiles")
    public static Object[][] getUserProfiles() {
        return new Object[][] {
            {"demouser", "Demo", "User", "demo@example.com"},
            {"fav_user", "Favorite", "User", "fav@example.com"},
            {"existing_orders_user", "Orders", "User", "orders@example.com"}
        };
    }
    
    /**
     * Data provider for negative test scenarios
     * @return Object[][] array containing invalid data
     */
    @DataProvider(name = "negativeTestData")
    public static Object[][] getNegativeTestData() {
        return new Object[][] {
            {"", "", "", "", ""},  // All empty fields
            {"A", "B", "C", "D", "1"},  // Too short data
            {null, null, null, null, null},  // Null values
            {"Very Long First Name That Exceeds Normal Limits", 
             "Very Long Last Name That Exceeds Normal Limits", 
             "Very Long Address That Exceeds Normal Character Limits For Address Fields", 
             "Very Long State Name", 
             "Very Long Postal Code"}  // Too long data
        };
    }
    
    /**
     * Data provider for performance test scenarios
     * @return Object[][] array containing load test parameters
     */
    @DataProvider(name = "loadTestData")
    public static Object[][] getLoadTestData() {
        return new Object[][] {
            {1, "Single User Test"},
            {5, "Small Load Test"},
            {10, "Medium Load Test"}
        };
    }
    
    /**
     * Data provider for mobile device simulation
     * @return Object[][] array containing device information
     */
    @DataProvider(name = "mobileDevices")
    public static Object[][] getMobileDevices() {
        return new Object[][] {
            {"iPhone 12", 390, 844},
            {"Samsung Galaxy S21", 384, 854},
            {"iPad", 768, 1024},
            {"Android Tablet", 800, 1280}
        };
    }
    
    /**
     * Data provider for API endpoints testing
     * @return Object[][] array containing API endpoint data
     */
    @DataProvider(name = "apiEndpoints")
    public static Object[][] getApiEndpoints() {
        return new Object[][] {
            {"/api/products", "GET"},
            {"/api/login", "POST"},
            {"/api/cart", "POST"},
            {"/api/checkout", "POST"}
        };
    }
}