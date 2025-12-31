package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.WaitUtils;
import driver.DriverManager;

public class ProductsUITest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        DriverManager.setDriver(driver);   // ✅ IMPORTANT
        driver.manage().window().maximize();
    }

    @Test
    public void addProductToCartAndLogin() {

        // Positive Scenario
        driver.get("https://automationexercise.com/products");

        // Handle ad iframe if present
        try {
            WebElement adFrame = driver.findElement(By.id("aswift_3"));
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.display='none';", adFrame);
        } catch (Exception ignored) {
        }

        // Add first product to cart
        WebElement firstProductAddBtn =
                driver.findElement(By.xpath("(//a[contains(text(),'Add to cart')])[1]"));
        WaitUtils.waitForElementClickable(firstProductAddBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", firstProductAddBtn);

        // Wait for cart popup
        WebElement cartPopup = driver.findElement(By.id("cartModal"));
        WaitUtils.waitForElementVisible(cartPopup);

        // View Cart
        WebElement viewCartBtn =
                driver.findElement(By.xpath("//a[@href='/view_cart']"));
        WaitUtils.waitForElementClickable(viewCartBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewCartBtn);

        // Proceed to Checkout
        WebElement proceedBtn =
                driver.findElement(By.xpath("//a[contains(text(),'Proceed To Checkout')]"));
        WaitUtils.waitForElementClickable(proceedBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", proceedBtn);

        // Register / Login
        WebElement registerLoginBtn =
                driver.findElement(By.xpath("//*[@id='checkoutModal']//a/u"));
        WaitUtils.waitForElementClickable(registerLoginBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", registerLoginBtn);

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ujitha@gmail.com", "uji@123"); // valid creds

        // Verify Logout button
        WebElement logoutBtn =
                driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        WaitUtils.waitForElementVisible(logoutBtn);

        Assert.assertTrue(logoutBtn.isDisplayed(),
                "Logout button is not displayed!");

        System.out.println("Positive Test: Product added and login successful");
    }

    @Test
    public void proceedToCheckoutWithEmptyCart() {

        driver.get("https://automationexercise.com/products");

        // Open Cart directly
        WebElement viewCartBtn =
                driver.findElement(By.xpath("//a[@href='/view_cart']"));
        WaitUtils.waitForElementClickable(viewCartBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewCartBtn);

        // Validate empty cart message
        WebElement emptyCartMsg =
                driver.findElement(By.xpath("//*[contains(text(),'Cart is empty')]"));
        WaitUtils.waitForElementVisible(emptyCartMsg);

        Assert.assertTrue(emptyCartMsg.isDisplayed(),
                "Empty cart message not displayed!");

        System.out.println("Negative Test: Empty cart message displayed");

        // click "here" link
        try {
            WebElement hereLink =
                    emptyCartMsg.findElement(By.xpath(".//a[contains(text(),'here')]"));
            WaitUtils.waitForElementClickable(hereLink);
            hereLink.click();
            System.out.println("Navigated back to products page");
        } catch (NoSuchElementException ignored) {
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
