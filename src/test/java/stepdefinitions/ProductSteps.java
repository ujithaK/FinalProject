package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import pages.LoginPage;

import java.time.Duration;

public class ProductSteps {

    WebDriver driver;
    WebDriverWait wait;

    // ===================== SETUP =====================
    @Given("User launches the AutomationExercise application")
    public void user_launches_application() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ===================== PRODUCTS PAGE =====================
    @Given("User is on the Products page")
    public void user_is_on_the_products_page() {
        driver.get("https://automationexercise.com/products");
    }

    // ===================== ADD TO CART =====================
    @When("User adds the first product to the cart")
    public void user_adds_the_first_product_to_the_cart() {

        // Handle ads if present
        try {
            WebElement adFrame = driver.findElement(By.id("aswift_3"));
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.display='none';", adFrame);
        } catch (Exception ignored) {}

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//a[contains(text(),'Add to cart')])[1]"))
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", addToCartBtn);

        // Wait for cart modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
    }

    // ===================== VIEW CART =====================
    @When("User clicks on {string}")
    public void user_clicks_on(String buttonName) {

        if (buttonName.equalsIgnoreCase("View Cart")) {

            WebElement viewCartBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[@href='/view_cart']"))
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", viewCartBtn);

            wait.until(ExpectedConditions.urlContains("view_cart"));
        }

        if (buttonName.equalsIgnoreCase("Proceed To Checkout")) {

            WebElement proceedBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(text(),'Proceed To Checkout')]"))
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", proceedBtn);
        }

        if (buttonName.equalsIgnoreCase("Register / Login")) {

            WebElement registerLoginBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@id='checkoutModal']//a/u"))
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", registerLoginBtn);
        }
    }

    // ===================== LOGIN =====================
    @When("User logs in with email {string} and password {string}")
    public void user_logs_in(String email, String password) {

        // Switch tab if opened
        String parent = driver.getWindowHandle();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
                break;
            }
        }

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
    }

    // ===================== ASSERT LOGIN =====================
    @Then("Logout button should be displayed")
    public void logout_button_should_be_displayed() {

        WebElement logoutBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(text(),'Logout')]"))
        );

        Assert.assertTrue(logoutBtn.isDisplayed(),
                "Logout button not visible – Login failed");

        driver.quit();
    }

    // ===================== NEGATIVE SCENARIO =====================
    @When("User opens cart without adding any product")
    public void user_opens_cart_without_product() {

        WebElement viewCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='/view_cart']"))
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewCartBtn);
    }

    @Then("Empty cart message should be displayed")
    public void empty_cart_message_should_be_displayed() {

        // Ensure we are on cart page
        wait.until(ExpectedConditions.urlContains("view_cart"));

        // Correct empty cart message
        WebElement emptyCartMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Cart is empty')]"))
        );

        Assert.assertTrue(
                emptyCartMsg.isDisplayed(),
                "Empty cart message is NOT displayed"
        );

        System.out.println("✅ Negative Test Passed: Cart is empty message displayed");

        try {
            WebElement hereLink = driver.findElement(
                    By.xpath("//a[contains(text(),'here')]"));
            hereLink.click();
            System.out.println("Navigated back to products page");
        } catch (NoSuchElementException e) {
            System.out.println("'Here' link not present");
        }

        driver.quit();
    }

}
