package ui;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class UITests {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ================== 1) Add product to cart (Positive) ==================
    @Test(priority = 1)
    public void addProductToCart() {
        driver.get("https://automationexercise.com/products");

        WebElement firstAddBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(text(),'Add to cart')])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstAddBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        Assert.assertTrue(driver.findElement(By.id("cartModal")).isDisplayed(),
                "Add to cart popup should be visible");
    }

    // ================== 2) Login with valid credentials (Positive) ==================
    @Test(priority = 2)
    public void loginWithValidCredentials() {
        driver.get("https://automationexercise.com/login");

        driver.findElement(By.name("email")).sendKeys("ujitha@gmail.com");
        driver.findElement(By.name("password")).sendKeys("uji@123");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logout')]")));
        Assert.assertTrue(logoutBtn.isDisplayed(), "Logout should be visible after login");
    }

    // ================== 3) Search product (Positive) ==================
    @Test
    public void searchProduct() {
        driver.get("https://automationexercise.com/products");

        WebElement searchInput = driver.findElement(By.xpath("//*[@id=\"search_product\"]"));
        searchInput.sendKeys("dress");

        driver.findElement(By.xpath("//*[@id=\"submit_search\"]/i")).click();

        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("/html/body/section[2]/div/div/div[2]/div/h2")
        ));

        Assert.assertTrue(results.size() > 0, "Search should return results for 'dress'");
    }

    // ================== 4) Proceed to checkout with product in cart (Positive) ==================
    @Test
    public void proceedToCheckoutWithCart() {
        driver.get("https://automationexercise.com/login");

        driver.findElement(By.name("email")).sendKeys("ujitha@gmail.com");
        driver.findElement(By.name("password")).sendKeys("uji@123");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();


        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(text(),'Add to cart')])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        WebElement viewCart = driver.findElement(By.xpath("//a[@href='/view_cart']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);

        WebElement proceed = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Proceed To Checkout')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceed);

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should navigate to checkout page when cart has products");
    }

    // ================== 5) Logout after login (Positive) ==================
    @Test
    public void logoutTest() {
        loginWithValidCredentials(); // Reuse login method
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Logout')]")));
        logoutBtn.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "After logout, user should be on login page");
    }

    // ================== 6) Login with invalid credentials (Negative) ==================
    @Test
    public void loginWithInvalidCredentials() {
        driver.get("https://automationexercise.com/login");

        driver.findElement(By.name("email")).sendKeys("wronguser@test.com");
        driver.findElement(By.name("password")).sendKeys("wrongPass");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Your email or password is incorrect')]")));
        Assert.assertTrue(error.isDisplayed(), "Invalid login should display an error message");
    }

    // ================== 7) Proceed to checkout with empty cart (Negative) ==================
    @Test
    public void proceedToCheckoutWithEmptyCart() {
        driver.get("https://automationexercise.com/products");

        WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view_cart']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);

        WebElement emptyMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"empty_cart\"]/p/b")));
        Assert.assertTrue(emptyMsg.isDisplayed(), "Cart is empty!");
    }

    // ================== 8) Add out-of-stock product (Negative / boundary) ==================
    // ================== Positive UI Test Case ==================
// Scenario: Navigate to Women → Dress and verify the header message
    @Test
    public void navigateToWomenDressAndVerifyMessage() {
        driver.get("https://automationexercise.com/products");

        // Click on 'Women' category
        WebElement womenCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"accordian\"]/div[1]/div[1]/h4/a")));
        womenCategory.click();

        // Click on 'Dress' sub-category
        WebElement dressSubCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"Women\"]/div/ul/li[1]/a")));
        dressSubCategory.click();

        // Verify "Women - Dress Products" message is visible
        try {
            WebElement headerMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/section/div/div[2]/div[2]/div/h2")));
            Assert.assertTrue(headerMsg.isDisplayed(), "Women - Dress Products");
            System.out.println("Positive Test Case: 'Women - Dress Products' message is displayed.");
        } catch (TimeoutException e) {
            Assert.fail("'Women - Dress Products' message not found. Test failed.");
        }
    }


    // ================== 9) Add maximum quantity (Boundary) ==================
    // ================== Boundary Test ==================
// Scenario: Verify quantity field accepts large values (boundary condition)

    @Test
    public void verifyQuantityFieldWithMaxValue() {

        driver.get("https://automationexercise.com/products");

        // Step 1: Click on View Product
        WebElement viewProductBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(text(),'View Product')])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProductBtn);

        // Step 2: Verify Quantity field is present
        WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("quantity")));

        Assert.assertTrue(quantityField.isDisplayed(),
                "Quantity field is NOT displayed on product details page");

        // Step 3: Click on Quantity field
        quantityField.click();

        // Step 4: Clear default value and enter large quantity (boundary value)
        quantityField.clear();
        quantityField.sendKeys("1000");

        // Step 5: Validate input value was entered successfully
        String enteredValue = quantityField.getAttribute("value");
        Assert.assertEquals(enteredValue, "1000",
                "Quantity field accept large value");

        // Test Passed
        System.out.println("Boundary Test Passed: Quantity field accepts large input value.");
    }

    // ================== 10) Form field boundary test (Name length) ==================
    @Test
    public void formFieldBoundaryTest() {
        driver.get("https://automationexercise.com/signup");

        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("A".repeat(256)); // test maximum length

        WebElement emailInput = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]"));
        emailInput.sendKeys("testboundary@example.com");

        driver.findElement(By.xpath("//button[contains(text(),'Signup')]")).click();

        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"form\"]/div/div/div/div/h2/b")));
        Assert.assertTrue(msg.isDisplayed(), "Enter Account Information");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
