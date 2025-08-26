package com.bstack.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Page Object Model for BStack Demo Home Page
 * URL: https://bstackdemo.com/?signin=true
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class HomePage extends BasePage {
    
    // Page URL
    private static final String HOME_URL = "https://bstackdemo.com/";
    
    // Header Elements
    @FindBy(className = "username")
    private WebElement loggedInUsername;
    
    @FindBy(id = "logout")
    private WebElement logoutButton;
    
    @FindBy(className = "bag")
    private WebElement cartIcon;
    
    @FindBy(className = "bag-quantity")
    private WebElement cartQuantity;
    
    // Product Filter Elements
    @FindBy(xpath = "//span[text()='Apple']")
    private WebElement appleFilter;
    
    @FindBy(xpath = "//span[text()='Samsung']")
    private WebElement samsungFilter;
    
    @FindBy(xpath = "//span[text()='OnePlus']")
    private WebElement onePlusFilter;
    
    @FindBy(xpath = "//span[text()='Google']")
    private WebElement googleFilter;
    
    // Product Elements
    @FindBy(xpath = "//div[@class='shelf-item']")
    private List<WebElement> productItems;
    
    @FindBy(xpath = "//div[@class='shelf-item'][1]//div[@class='shelf-item__buy-btn']")
    private WebElement firstProductAddToCartButton;
    
    @FindBy(xpath = "//div[@class='shelf-item'][1]//p[@class='shelf-item__title']")
    private WebElement firstProductTitle;
    
    @FindBy(xpath = "//div[@class='shelf-item'][1]//div[@class='shelf-item__price']")
    private WebElement firstProductPrice;
    
    // Favourites Elements
    @FindBy(xpath = "//div[@class='shelf-item'][1]//div[@class='shelf-item__favourite']")
    private WebElement firstProductFavouriteIcon;
    
    @FindBy(xpath = "//span[text()='Favourites']")
    private WebElement favouritesFilter;
    
    // Sort Elements
    @FindBy(className = "sort")
    private WebElement sortDropdown;
    
    @FindBy(xpath = "//option[text()='Lowest to highest']")
    private WebElement sortLowToHigh;
    
    @FindBy(xpath = "//option[text()='Highest to lowest']")
    private WebElement sortHighToLow;
    
    // Page Elements
    @FindBy(xpath = "//h2[text()='StackDemo']")
    private WebElement pageTitle;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to home page
     * @return HomePage instance for method chaining
     */
    public HomePage navigateToHomePage() {
        navigateToUrl(HOME_URL);
        waitForPageLoad();
        logger.info("Navigated to home page");
        return this;
    }
    
    /**
     * Check if user is logged in
     * @return boolean true if user is logged in
     */
    public boolean isUserLoggedIn() {
        boolean isLoggedIn = isElementDisplayed(loggedInUsername);
        logger.info("User logged in: " + isLoggedIn);
        return isLoggedIn;
    }
    
    /**
     * Get logged in username
     * @return String username
     */
    public String getLoggedInUsername() {
        if (isUserLoggedIn()) {
            return getElementText(loggedInUsername);
        }
        return "";
    }
    
    /**
     * Click logout button
     * @return LoginPage instance
     */
    public LoginPage logout() {
        clickElement(logoutButton);
        logger.info("Clicked logout button");
        return new LoginPage(driver);
    }
    
    /**
     * Add first product to cart
     * @return HomePage instance for method chaining
     */
    public HomePage addFirstProductToCart() {
        scrollToElement(firstProductAddToCartButton);
        clickElement(firstProductAddToCartButton);
        logger.info("Added first product to cart");
        return this;
    }
    
    /**
     * Get first product title
     * @return String product title
     */
    public String getFirstProductTitle() {
        return getElementText(firstProductTitle);
    }
    
    /**
     * Get first product price
     * @return String product price
     */
    public String getFirstProductPrice() {
        return getElementText(firstProductPrice);
    }
    
    /**
     * Get cart quantity
     * @return int cart quantity
     */
    public int getCartQuantity() {
        if (isElementDisplayed(cartQuantity)) {
            String quantity = getElementText(cartQuantity);
            return Integer.parseInt(quantity);
        }
        return 0;
    }
    
    /**
     * Click on cart icon to go to checkout
     * @return CheckoutPage instance
     */
    public CheckoutPage goToCart() {
        clickElement(cartIcon);
        logger.info("Clicked on cart icon");
        return new CheckoutPage(driver);
    }
    
    /**
     * Filter products by Apple brand
     * @return HomePage instance for method chaining
     */
    public HomePage filterByApple() {
        clickElement(appleFilter);
        logger.info("Filtered products by Apple");
        return this;
    }
    
    /**
     * Filter products by Samsung brand
     * @return HomePage instance for method chaining
     */
    public HomePage filterBySamsung() {
        clickElement(samsungFilter);
        logger.info("Filtered products by Samsung");
        return this;
    }
    
    /**
     * Filter products by OnePlus brand
     * @return HomePage instance for method chaining
     */
    public HomePage filterByOnePlus() {
        clickElement(onePlusFilter);
        logger.info("Filtered products by OnePlus");
        return this;
    }
    
    /**
     * Filter products by Google brand
     * @return HomePage instance for method chaining
     */
    public HomePage filterByGoogle() {
        clickElement(googleFilter);
        logger.info("Filtered products by Google");
        return this;
    }
    
    /**
     * Add first product to favourites
     * @return HomePage instance for method chaining
     */
    public HomePage addFirstProductToFavourites() {
        clickElement(firstProductFavouriteIcon);
        logger.info("Added first product to favourites");
        return this;
    }
    
    /**
     * Filter products by favourites
     * @return HomePage instance for method chaining
     */
    public HomePage filterByFavourites() {
        clickElement(favouritesFilter);
        logger.info("Filtered products by favourites");
        return this;
    }
    
    /**
     * Sort products from lowest to highest price
     * @return HomePage instance for method chaining
     */
    public HomePage sortByPriceLowToHigh() {
        clickElement(sortDropdown);
        clickElement(sortLowToHigh);
        logger.info("Sorted products by price: lowest to highest");
        return this;
    }
    
    /**
     * Sort products from highest to lowest price
     * @return HomePage instance for method chaining
     */
    public HomePage sortByPriceHighToLow() {
        clickElement(sortDropdown);
        clickElement(sortHighToLow);
        logger.info("Sorted products by price: highest to lowest");
        return this;
    }
    
    /**
     * Get number of products displayed
     * @return int number of products
     */
    public int getProductCount() {
        int count = productItems.size();
        logger.info("Number of products displayed: " + count);
        return count;
    }
    
    /**
     * Check if home page is displayed
     * @return boolean true if home page is displayed
     */
    public boolean isHomePageDisplayed() {
        boolean isDisplayed = isElementDisplayed(pageTitle);
        logger.info("Home page displayed: " + isDisplayed);
        return isDisplayed;
    }
    
    /**
     * Verify if we are on the correct home page
     * @return boolean true if on correct home page
     */
    public boolean isOnHomePage() {
        return getCurrentUrl().contains("bstackdemo.com") && isHomePageDisplayed();
    }
    
    /**
     * Get home page title
     * @return String page title
     */
    public String getHomePageTitle() {
        return getPageTitle();
    }
}