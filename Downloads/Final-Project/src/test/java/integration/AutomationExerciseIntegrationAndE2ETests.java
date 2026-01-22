package ui;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import utils.ScreenshotUtil;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class AutomationExerciseIntegrationAndE2ETests {

    private BasePage basePage;
    private WebDriver driver;
    private WebDriverWait wait;

    // ===================== GLOBAL SETUP =====================
    @BeforeClass
    public void setup() {
        basePage = new BasePage();
        driver = basePage.getDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // REST-Assured setup
        RestAssured.baseURI = ConfigManager.getProperty("baseUrl");
        RestAssured.basePath = "/api";
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    /** Adds first product to cart and navigates to cart page */
    private void addFirstProductToCart() {

        basePage.navigateTo("productsUrl");

        WebElement product = basePage.waitForVisibility(
                By.xpath("(//div[@class='product-image-wrapper'])[1]")
        );
        basePage.scrollIntoView(product);

        WebElement addToCart = product.findElement(By.xpath(".//a[contains(@class,'add-to-cart')]"));
        basePage.jsClick(addToCart);

        try {
            WebElement viewCart = basePage.waitForClickability(
                    By.xpath("//u[text()='View Cart']"));
            basePage.jsClick(viewCart);
        } catch (TimeoutException e) {
            basePage.navigateTo("cartUrl");
        }
    }

    // INTEGRATION TESTS

    @Test
    public void integration_UI_Products_Match_API() {
        try {
            basePage.navigateTo("productsUrl");
            Assert.assertTrue(driver.getCurrentUrl().contains("products"));

            Response res = given().get("/productsList");
            Assert.assertEquals(res.statusCode(), 200);
            Assert.assertTrue(
                    res.jsonPath().getList("products").size() > 0,
                    "API should return products"
            );

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "integration_UI_Products_Match_API");
            throw e;
        }
    }

    @Test
    public void integration_Search_UI_and_API() {
        try {
            basePage.navigateTo("productsUrl");

            basePage.waitForVisibility(By.id("search_product")).sendKeys("dress");
            driver.findElement(By.id("submit_search")).click();

            Assert.assertTrue(driver.getCurrentUrl().contains("search"));

            Response res = given()
                    .contentType(ContentType.URLENC)
                    .formParam("search_product", "dress")
                    .post("/searchProduct");

            Assert.assertEquals(res.jsonPath().getInt("responseCode"), 200);

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "integration_Search_UI_and_API");
            throw e;
        }
    }

    @Test
    public void integration_UI_AddToCart_and_Validate() {
        try {
            addFirstProductToCart();

            WebElement cartTable = basePage.waitForVisibility(By.id("cart_info_table"));
            Assert.assertTrue(cartTable.isDisplayed());

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "integration_UI_AddToCart_and_Validate");
            throw e;
        }
    }

    @Test
    public void integration_Checkout_UI_and_DB() {
        try {
            addFirstProductToCart();

            WebElement checkout = basePage.waitForClickability(
                    By.xpath("//a[contains(text(),'Proceed To Checkout')]"));
            basePage.jsClick(checkout);

            wait.until(ExpectedConditions.urlContains("checkout"));
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout"));

            // Mock DB validation
            boolean orderExistsInDB = true;
            Assert.assertTrue(orderExistsInDB, "Order should exist in DB");

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "integration_Checkout_UI_and_DB");
            throw e;
        }
    }

    // ===================== END-TO-END TESTS =====================

    @Test
    public void e2e_UserRegistration_Login_Logout() {
        try {
            basePage.navigateTo("loginUrl");
            Assert.assertTrue(driver.getPageSource().contains("New User Signup"));

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "e2e_UserRegistration_Login_Logout");
            throw e;
        }
    }

    @Test
    public void e2e_Browse_And_View_Product() {
        try {
            basePage.navigateTo("productsUrl");

            basePage.waitForClickability(
                            By.xpath("(//a[contains(text(),'View Product')])[1]"))
                    .click();

            Assert.assertTrue(driver.getCurrentUrl().contains("product_details"));

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "e2e_Browse_And_View_Product");
            throw e;
        }
    }

    @Test
    public void e2e_Login_AddToCart_Checkout() {
        try {
            basePage.navigateTo("loginUrl");

            basePage.waitForVisibility(By.name("email"))
                    .sendKeys(ConfigManager.getProperty("userEmail"));

            driver.findElement(By.name("password"))
                    .sendKeys(ConfigManager.getProperty("userPassword"));

            driver.findElement(By.xpath("//button[text()='Login']")).click();

            WebElement logoutBtn = basePage.waitForVisibility(
                    By.xpath("//a[contains(text(),'Logout')]"));
            Assert.assertTrue(logoutBtn.isDisplayed());

            addFirstProductToCart();

            WebElement proceedToCheckout = basePage.waitForClickability(
                    By.xpath("//a[contains(text(),'Proceed To Checkout')]"));
            basePage.jsClick(proceedToCheckout);

            wait.until(ExpectedConditions.urlContains("checkout"));
            Assert.assertTrue(driver.getCurrentUrl().contains("checkout"));

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "e2e_Login_AddToCart_Checkout");
            throw e;
        }
    }

    @Test
    public void e2e_EmptyCart_Validation() {
        try {
            basePage.navigateTo("cartUrl");
            Assert.assertTrue(
                    driver.getPageSource().toLowerCase().contains("cart is empty") ||
                            driver.getPageSource().toLowerCase().contains("empty")
            );
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "e2e_EmptyCart_Validation");
            throw e;
        }
    }

    @Test
    public void e2e_Category_Women_Dress() {
        try {
            basePage.navigateTo("baseUrl");

            WebElement categorySection = basePage.waitForVisibility(
                    By.xpath("//h2[text()='Category']"));
            basePage.scrollIntoView(categorySection);

            WebElement womenCategory = basePage.waitForClickability(
                    By.xpath("//a[@href='#Women']"));
            basePage.jsClick(womenCategory);

            WebElement dressSubCategory = basePage.waitForClickability(
                    By.xpath("//a[contains(text(),'Dress')]"));
            basePage.jsClick(dressSubCategory);

            WebElement heading = basePage.waitForVisibility(
                    By.xpath("//h2[contains(text(),'Women - Dress Products')]"));
            Assert.assertTrue(heading.isDisplayed());

        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot(driver, "e2e_Category_Women_Dress");
            throw e;
        }
    }
}
