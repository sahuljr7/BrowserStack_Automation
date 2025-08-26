package com.bstack.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Page Object Model for BStack Demo Checkout Page
 * URL: https://bstackdemo.com/checkout
 * 
 * @author Test Automation Engineer
 * @version 1.0
 */
public class CheckoutPage extends BasePage {
    
    // Page URL
    private static final String CHECKOUT_URL = "https://bstackdemo.com/checkout";
    
    // Cart Items
    @FindBy(xpath = "//div[@class='float-cart__content']//div[@class='shelf-item']")
    private List<WebElement> cartItems;
    
    @FindBy(xpath = "//p[@class='shelf-item__title']")
    private List<WebElement> cartItemTitles;
    
    @FindBy(xpath = "//div[@class='shelf-item__price']")
    private List<WebElement> cartItemPrices;
    
    @FindBy(xpath = "//button[@class='shelf-item__del']")
    private List<WebElement> removeItemButtons;
    
    // Cart Summary
    @FindBy(className = "float-cart__close-btn")
    private WebElement closeCartButton;
    
    @FindBy(className = "sub-price__val")
    private WebElement subtotalAmount;
    
    @FindBy(xpath = "//p[contains(@class,'total-price')]/span")
    private WebElement totalAmount;
    
    @FindBy(className = "buy-btn")
    private WebElement checkoutButton;
    
    // Checkout Form Elements
    @FindBy(id = "firstNameInput")
    private WebElement firstNameInput;
    
    @FindBy(id = "lastNameInput")
    private WebElement lastNameInput;
    
    @FindBy(id = "addressLine1Input")
    private WebElement addressLine1Input;
    
    @FindBy(id = "provinceInput")
    private WebElement stateInput;
    
    @FindBy(id = "postCodeInput")
    private WebElement postalCodeInput;
    
    @FindBy(id = "checkout-btn")
    private WebElement submitOrderButton;
    
    // Order Confirmation
    @FindBy(className = "optimizedCheckout-orderSummary")
    private WebElement orderSummary;
    
    @FindBy(xpath = "//div[@class='optimizedCheckout-contentPrimary']//h1")
    private WebElement orderConfirmationHeader;
    
    @FindBy(id = "confirmation-message")
    private WebElement confirmationMessage;
    
    // Navigation Elements
    @FindBy(linkText = "StackDemo")
    private WebElement homeLink;
    
    @FindBy(className = "shelf-empty")
    private WebElement emptyCartMessage;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to checkout page
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage navigateToCheckoutPage() {
        navigateToUrl(CHECKOUT_URL);
        waitForPageLoad();
        logger.info("Navigated to checkout page");
        return this;
    }
    
    /**
     * Get number of items in cart
     * @return int number of items
     */
    public int getCartItemCount() {
        int count = cartItems.size();
        logger.info("Number of items in cart: " + count);
        return count;
    }
    
    /**
     * Get cart item title by index
     * @param index Item index (0-based)
     * @return String item title
     */
    public String getCartItemTitle(int index) {
        if (index < cartItemTitles.size()) {
            return getElementText(cartItemTitles.get(index));
        }
        logger.error("Item index out of bounds: " + index);
        return "";
    }
    
    /**
     * Get cart item price by index
     * @param index Item index (0-based)
     * @return String item price
     */
    public String getCartItemPrice(int index) {
        if (index < cartItemPrices.size()) {
            return getElementText(cartItemPrices.get(index));
        }
        logger.error("Item index out of bounds: " + index);
        return "";
    }
    
    /**
     * Remove item from cart by index
     * @param index Item index (0-based)
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage removeCartItem(int index) {
        if (index < removeItemButtons.size()) {
            clickElement(removeItemButtons.get(index));
            logger.info("Removed item at index: " + index);
        } else {
            logger.error("Remove button index out of bounds: " + index);
        }
        return this;
    }
    
    /**
     * Get subtotal amount
     * @return String subtotal amount
     */
    public String getSubtotalAmount() {
        return getElementText(subtotalAmount);
    }
    
    /**
     * Get total amount
     * @return String total amount
     */
    public String getTotalAmount() {
        return getElementText(totalAmount);
    }
    
    /**
     * Click checkout button to proceed to checkout form
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage clickCheckoutButton() {
        clickElement(checkoutButton);
        logger.info("Clicked checkout button");
        return this;
    }
    
    /**
     * Fill in first name
     * @param firstName First name to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterFirstName(String firstName) {
        enterText(firstNameInput, firstName);
        logger.info("Entered first name: " + firstName);
        return this;
    }
    
    /**
     * Fill in last name
     * @param lastName Last name to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterLastName(String lastName) {
        enterText(lastNameInput, lastName);
        logger.info("Entered last name: " + lastName);
        return this;
    }
    
    /**
     * Fill in address line 1
     * @param address Address to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterAddress(String address) {
        enterText(addressLine1Input, address);
        logger.info("Entered address: " + address);
        return this;
    }
    
    /**
     * Fill in state/province
     * @param state State to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterState(String state) {
        enterText(stateInput, state);
        logger.info("Entered state: " + state);
        return this;
    }
    
    /**
     * Fill in postal code
     * @param postalCode Postal code to enter
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage enterPostalCode(String postalCode) {
        enterText(postalCodeInput, postalCode);
        logger.info("Entered postal code: " + postalCode);
        return this;
    }
    
    /**
     * Fill complete checkout form
     * @param firstName First name
     * @param lastName Last name
     * @param address Address
     * @param state State
     * @param postalCode Postal code
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillCheckoutForm(String firstName, String lastName, 
                                       String address, String state, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterAddress(address);
        enterState(state);
        enterPostalCode(postalCode);
        logger.info("Filled complete checkout form");
        return this;
    }
    
    /**
     * Submit the order
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage submitOrder() {
        clickElement(submitOrderButton);
        logger.info("Submitted order");
        return this;
    }
    
    /**
     * Complete checkout process with provided details
     * @param firstName First name
     * @param lastName Last name
     * @param address Address
     * @param state State
     * @param postalCode Postal code
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage completeCheckout(String firstName, String lastName, 
                                       String address, String state, String postalCode) {
        clickCheckoutButton();
        fillCheckoutForm(firstName, lastName, address, state, postalCode);
        submitOrder();
        logger.info("Completed checkout process");
        return this;
    }
    
    /**
     * Check if order confirmation is displayed
     * @return boolean true if order confirmation is displayed
     */
    public boolean isOrderConfirmationDisplayed() {
        return isElementDisplayed(confirmationMessage) || 
               isElementDisplayed(orderConfirmationHeader);
    }
    
    /**
     * Get confirmation message
     * @return String confirmation message
     */
    public String getConfirmationMessage() {
        if (isElementDisplayed(confirmationMessage)) {
            return getElementText(confirmationMessage);
        }
        return "";
    }
    
    /**
     * Check if cart is empty
     * @return boolean true if cart is empty
     */
    public boolean isCartEmpty() {
        return isElementDisplayed(emptyCartMessage) || cartItems.isEmpty();
    }
    
    /**
     * Close cart and return to home page
     * @return HomePage instance
     */
    public HomePage closeCartAndReturnHome() {
        clickElement(closeCartButton);
        logger.info("Closed cart");
        return new HomePage(driver);
    }
    
    /**
     * Click home link to return to home page
     * @return HomePage instance
     */
    public HomePage returnToHome() {
        clickElement(homeLink);
        logger.info("Returned to home page");
        return new HomePage(driver);
    }
    
    /**
     * Verify if we are on the correct checkout page
     * @return boolean true if on correct checkout page
     */
    public boolean isOnCheckoutPage() {
        return getCurrentUrl().contains("checkout");
    }
    
    /**
     * Get checkout page title
     * @return String page title
     */
    public String getCheckoutPageTitle() {
        return getPageTitle();
    }
}