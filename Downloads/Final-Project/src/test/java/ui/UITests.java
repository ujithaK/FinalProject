package ui;

import config.ConfigManager;
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
    String baseUrl;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // load from config.properties
        baseUrl = ConfigManager.getProperty("baseUrl");
    }

    @Test(priority = 1)
    public void addProductToCart() {
        driver.get(baseUrl + "/products");

        WebElement firstAddBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(text(),'Add to cart')])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstAddBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        Assert.assertTrue(driver.findElement(By.id("cartModal")).isDisplayed(),
                "Add to cart popup should be visible");
    }

    @Test(priority = 2)
    public void loginWithValidCredentials() {
        driver.get(baseUrl + "/login");

        driver.findElement(By.name("email")).sendKeys("ujitha@gmail.com");
        driver.findElement(By.name("password")).sendKeys("uji@123");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logout')]")));
        Assert.assertTrue(logoutBtn.isDisplayed(), "Logout should be visible after login");
    }

    @Test
    public void searchProduct() {
        driver.get(baseUrl + "/products");

        WebElement searchInput = driver.findElement(By.id("search_product"));
        searchInput.sendKeys("dress");

        driver.findElement(By.id("submit_search")).click();

        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("/html/body/section[2]/div/div/div[2]/div/h2")
        ));

        Assert.assertTrue(results.size() > 0, "Search should return results for 'dress'");
    }

    @Test
    public void proceedToCheckoutWithCart() {
        driver.get(baseUrl + "/login");

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

    @Test
    public void logoutTest() {
        loginWithValidCredentials();
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Logout')]")));
        logoutBtn.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "After logout, user should be on login page");
    }

    @Test
    public void loginWithInvalidCredentials() {
        driver.get(baseUrl + "/login");

        driver.findElement(By.name("email")).sendKeys("wronguser@test.com");
        driver.findElement(By.name("password")).sendKeys("wrongPass");
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Your email or password is incorrect')]")));
        Assert.assertTrue(error.isDisplayed(), "Invalid login should display an error message");
    }

    @Test
    public void proceedToCheckoutWithEmptyCart() {
        driver.get(baseUrl + "/products");

        WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view_cart']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);

        WebElement emptyMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='empty_cart']/p/b")));
        Assert.assertTrue(emptyMsg.isDisplayed(), "Cart is empty!");
    }

    @Test
    public void navigateToWomenDressAndVerifyMessage() {
        driver.get(baseUrl + "/products");

        WebElement womenCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='accordian']/div[1]/div[1]/h4/a")));
        womenCategory.click();

        WebElement dressSubCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='Women']/div/ul/li[1]/a")));
        dressSubCategory.click();

        try {
            WebElement headerMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/section/div/div[2]/div[2]/div/h2")));
            Assert.assertTrue(headerMsg.isDisplayed(), "'Women - Dress Products' message displayed");
        } catch (TimeoutException e) {
            Assert.fail("'Women - Dress Products' message not found. Test failed.");
        }
    }

    @Test
    public void verifyQuantityFieldWithMaxValue() {
        driver.get(baseUrl + "/products");

        WebElement viewProductBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(text(),'View Product')])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProductBtn);

        WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("quantity")));

        quantityField.clear();
        quantityField.sendKeys("1000");

        Assert.assertEquals(quantityField.getAttribute("value"), "1000",
                "Quantity field should accept large values");
    }

    @Test
    public void formFieldBoundaryTest() {
        driver.get(baseUrl + "/signup");

        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("A".repeat(256));

        WebElement emailInput = driver.findElement(By.xpath("//*[@id='form']/div/div/div[3]/div/form/input[3]"));
        emailInput.sendKeys("testboundary@example.com");

        driver.findElement(By.xpath("//button[contains(text(),'Signup')]")).click();

        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='form']/div/div/div/div/h2/b")));
        Assert.assertTrue(msg.isDisplayed(), "Enter Account Information");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
