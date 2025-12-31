package ui;

import driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

public class ProductsUITest {

    WebDriver driver;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();

        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void addProductToCartAndLogin() {

        driver.get("https://automationexercise.com/products");

        productsPage.hideAdIfPresent();
        productsPage.addFirstProductToCart();
        productsPage.waitForCartPopup();
        productsPage.clickViewCart();

        cartPage.proceedToCheckout();
        checkoutPage.clickRegisterOrLogin();

        loginPage.login("ujitha@gmail.com", "uji@123");

        WebElement logoutBtn =
                driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        Assert.assertTrue(logoutBtn.isDisplayed(),
                "Logout button is not displayed!");

        System.out.println("Positive Test: Product added and login successful");
    }

    @Test
    public void proceedToCheckoutWithEmptyCart() {

        driver.get("https://automationexercise.com/products");

        productsPage.clickViewCart();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Empty cart message not displayed!");

        cartPage.clickHereLinkIfPresent();
        System.out.println("Negative Test: Empty cart handled correctly");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
