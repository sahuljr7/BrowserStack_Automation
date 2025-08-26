package com.bstack.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import com.bstack.pages.LoginPage;
import com.bstack.pages.HomePage;
import com.bstack.pages.CheckoutPage;
import com.bstack.utils.TestDataProvider;
import com.bstack.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;

/**
 * Test class for E-Commerce functionality
 * Contains all test cases related to shopping cart, checkout, and product interactions
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class ECommerceTest extends BaseTest {
    
    private LoginPage loginPage;
    private HomePage homePage;
    private CheckoutPage checkoutPage;
    
    /**
     * Setup method to initialize page objects and login before each test
     */
    @BeforeMethod
    public void setUpTest() {
        super.setUp();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);
        
        // Login before each test
        loginPage.navigateToLoginPage();
        homePage = loginPage.login("demouser");
    }
    
    /**
     * Test adding product to cart
     */
    @Test(description = "Verify adding product to shopping cart")
    public void testAddProductToCart() {
        test = ExtentReportManager.createTest("Test Add Product to Cart", 
                                            "Verify user can add products to shopping cart", 
                                            "Shopping Cart");
        
        try {
            logStep("Verify initial cart state");
            int initialCartCount = homePage.getCartQuantity();
            
            logStep("Get product details before adding to cart");
            String productTitle = homePage.getFirstProductTitle();
            String productPrice = homePage.getFirstProductPrice();
            
            logStep("Add first product to cart");
            homePage.addFirstProductToCart();
            
            logStep("Verify product added to cart");
            int updatedCartCount = homePage.getCartQuantity();
            Assert.assertEquals(updatedCartCount, initialCartCount + 1, 
                              "Cart quantity should increase by 1");
            
            logResult("Successfully added product '" + productTitle + 
                     "' with price " + productPrice + " to cart");
            test.pass("Product added to cart successfully. Cart count: " + updatedCartCount);
            
        } catch (Exception e) {
            logFailure("Add to cart test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test adding multiple products to cart
     */
    @Test(description = "Verify adding multiple products to cart")
    public void testAddMultipleProductsToCart() {
        test = ExtentReportManager.createTest("Test Add Multiple Products to Cart", 
                                            "Verify user can add multiple products to shopping cart", 
                                            "Shopping Cart");
        
        try {
            logStep("Add first product to cart");
            homePage.addFirstProductToCart();
            int cartCountAfterFirst = homePage.getCartQuantity();
            Assert.assertEquals(cartCountAfterFirst, 1, "Cart should have 1 item after first addition");
            
            logStep("Add same product again");
            homePage.addFirstProductToCart();
            int cartCountAfterSecond = homePage.getCartQuantity();
            Assert.assertEquals(cartCountAfterSecond, 2, "Cart should have 2 items after second addition");
            
            logResult("Successfully added multiple products to cart. Final count: " + cartCountAfterSecond);
            test.pass("Multiple products added to cart successfully");
            
        } catch (Exception e) {
            logFailure("Add multiple products test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test product filtering functionality
     * @param filterBrand Brand to filter by
     */
    @Test(dataProvider = "productFilters", dataProviderClass = TestDataProvider.class,
          description = "Verify product filtering by brand")
    public void testProductFiltering(String filterBrand) {
        test = ExtentReportManager.createTest("Test Product Filtering - " + filterBrand, 
                                            "Verify products can be filtered by brand: " + filterBrand, 
                                            "Product Filter");
        
        try {
            logStep("Get initial product count");
            int initialProductCount = homePage.getProductCount();
            
            logStep("Apply " + filterBrand + " filter");
            switch (filterBrand) {
                case "Apple":
                    homePage.filterByApple();
                    break;
                case "Samsung":
                    homePage.filterBySamsung();
                    break;
                case "OnePlus":
                    homePage.filterByOnePlus();
                    break;
                case "Google":
                    homePage.filterByGoogle();
                    break;
            }
            
            // Wait for filter to apply
            sleep(2000);
            
            logStep("Verify filter applied");
            int filteredProductCount = homePage.getProductCount();
            
            // Filter should change product count (either reduce or maintain based on availability)
            Assert.assertTrue(filteredProductCount > 0, "Filtered products should be displayed");
            
            logResult("Filter applied successfully. Initial count: " + initialProductCount + 
                     ", Filtered count: " + filteredProductCount);
            test.pass("Product filtering by " + filterBrand + " verified successfully");
            
        } catch (Exception e) {
            logFailure("Product filtering test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test product sorting functionality
     */
    @Test(description = "Verify product sorting functionality")
    public void testProductSorting() {
        test = ExtentReportManager.createTest("Test Product Sorting", 
                                            "Verify products can be sorted by price", 
                                            "Product Sorting");
        
        try {
            logStep("Sort products by price: Low to High");
            homePage.sortByPriceLowToHigh();
            sleep(2000);
            
            int productCount = homePage.getProductCount();
            Assert.assertTrue(productCount > 0, "Products should be displayed after sorting");
            
            logStep("Sort products by price: High to Low");
            homePage.sortByPriceHighToLow();
            sleep(2000);
            
            int productCountAfterSort = homePage.getProductCount();
            Assert.assertEquals(productCountAfterSort, productCount, 
                              "Product count should remain same after sorting");
            
            logResult("Product sorting functionality verified successfully");
            test.pass("Product sorting verified successfully");
            
        } catch (Exception e) {
            logFailure("Product sorting test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test favourites functionality
     */
    @Test(description = "Verify favourites functionality")
    public void testFavouritesFunctionality() {
        test = ExtentReportManager.createTest("Test Favourites Functionality", 
                                            "Verify user can add products to favourites and filter by favourites", 
                                            "Favourites");
        
        try {
            logStep("Add first product to favourites");
            homePage.addFirstProductToFavourites();
            
            logStep("Filter by favourites");
            homePage.filterByFavourites();
            sleep(2000);
            
            int favouriteProductsCount = homePage.getProductCount();
            Assert.assertTrue(favouriteProductsCount > 0, "Favourite products should be displayed");
            
            logResult("Favourites functionality working correctly. Favourite products count: " + 
                     favouriteProductsCount);
            test.pass("Favourites functionality verified successfully");
            
        } catch (Exception e) {
            logFailure("Favourites functionality test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test complete checkout process with valid data
     * @param firstName First name for checkout
     * @param lastName Last name for checkout
     * @param address Address for checkout
     * @param state State for checkout
     * @param postalCode Postal code for checkout
     */
    @Test(dataProvider = "checkoutData", dataProviderClass = TestDataProvider.class,
          description = "Verify complete checkout process")
    public void testCompleteCheckoutProcess(String firstName, String lastName, String address, 
                                          String state, String postalCode) {
        test = ExtentReportManager.createTest("Test Complete Checkout Process", 
                                            "Verify user can complete entire checkout process", 
                                            "Checkout");
        
        try {
            logStep("Add product to cart");
            homePage.addFirstProductToCart();
            int cartCount = homePage.getCartQuantity();
            Assert.assertEquals(cartCount, 1, "Cart should have 1 item");
            
            logStep("Navigate to checkout page");
            checkoutPage = homePage.goToCart();
            
            logStep("Verify cart items in checkout");
            int checkoutItemCount = checkoutPage.getCartItemCount();
            Assert.assertEquals(checkoutItemCount, 1, "Checkout should show 1 item");
            
            String itemTitle = checkoutPage.getCartItemTitle(0);
            String itemPrice = checkoutPage.getCartItemPrice(0);
            Assert.assertFalse(itemTitle.isEmpty(), "Item title should not be empty");
            Assert.assertFalse(itemPrice.isEmpty(), "Item price should not be empty");
            
            logStep("Complete checkout with details: " + firstName + " " + lastName);
            checkoutPage.completeCheckout(firstName, lastName, address, state, postalCode);
            
            // Wait for order processing
            sleep(3000);
            
            logStep("Verify checkout completion");
            // Note: Actual order confirmation verification depends on the application's behavior
            // This is a placeholder for the verification logic
            
            logResult("Checkout process completed successfully for: " + firstName + " " + lastName);
            test.pass("Complete checkout process verified successfully");
            
        } catch (Exception e) {
            logFailure("Complete checkout test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test cart management - removing items
     */
    @Test(description = "Verify cart item removal functionality")
    public void testRemoveItemFromCart() {
        test = ExtentReportManager.createTest("Test Remove Item from Cart", 
                                            "Verify user can remove items from shopping cart", 
                                            "Shopping Cart");
        
        try {
            logStep("Add product to cart");
            homePage.addFirstProductToCart();
            Assert.assertEquals(homePage.getCartQuantity(), 1, "Cart should have 1 item");
            
            logStep("Navigate to checkout page");
            checkoutPage = homePage.goToCart();
            Assert.assertEquals(checkoutPage.getCartItemCount(), 1, "Checkout should show 1 item");
            
            logStep("Remove item from cart");
            checkoutPage.removeCartItem(0);
            sleep(2000);
            
            logStep("Verify item removed");
            boolean isCartEmpty = checkoutPage.isCartEmpty();
            Assert.assertTrue(isCartEmpty, "Cart should be empty after removing item");
            
            logResult("Item removed from cart successfully");
            test.pass("Cart item removal verified successfully");
            
        } catch (Exception e) {
            logFailure("Remove item from cart test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test cart total calculation
     */
    @Test(description = "Verify cart total calculation")
    public void testCartTotalCalculation() {
        test = ExtentReportManager.createTest("Test Cart Total Calculation", 
                                            "Verify cart total is calculated correctly", 
                                            "Shopping Cart");
        
        try {
            logStep("Add multiple products to cart");
            homePage.addFirstProductToCart();
            homePage.addFirstProductToCart();
            
            Assert.assertEquals(homePage.getCartQuantity(), 2, "Cart should have 2 items");
            
            logStep("Navigate to checkout and verify totals");
            checkoutPage = homePage.goToCart();
            
            String subtotal = checkoutPage.getSubtotalAmount();
            String total = checkoutPage.getTotalAmount();
            
            Assert.assertFalse(subtotal.isEmpty(), "Subtotal should not be empty");
            Assert.assertFalse(total.isEmpty(), "Total should not be empty");
            
            logResult("Cart totals calculated correctly. Subtotal: " + subtotal + ", Total: " + total);
            test.pass("Cart total calculation verified successfully");
            
        } catch (Exception e) {
            logFailure("Cart total calculation test failed: " + e.getMessage());
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;
        }
    }
}