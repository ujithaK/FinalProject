package stepdefinitions;

import config.ConfigManager;
import driver.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.*;

public class ProductsUITest {

    WebDriver driver;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    LoginPage loginPage;

    @Given("user is on products page")
    public void user_is_on_products_page() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();

        String baseUrl = ConfigManager.getProperty("baseUrl");
        driver.get(baseUrl + "/products");

        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);

        productsPage.hideAdIfPresent();
    }

    @When("user adds a product to cart")
    public void user_adds_a_product_to_cart() {
        productsPage.addFirstProductToCart();
        productsPage.waitForCartPopup();
        productsPage.clickViewCart();
    }

    @When("user proceeds to checkout page")
    public void user_proceeds_to_checkout_page() {
        cartPage.proceedToCheckout();
        checkoutPage.clickRegisterOrLogin();
    }

    @When("user logs in with {string} and {string}")
    public void user_logs_in_with_and(String email, String password) {
        loginPage.login(email, password);
    }

    @Then("user should see the logout button")
    public void user_should_see_the_logout_button() {
        Assert.assertTrue(
                driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).isDisplayed(),
                "Logout button is not displayed!"
        );
    }

    @When("user opens the cart without adding items")
    public void user_opens_the_cart_without_adding_items() {
        productsPage.clickViewCart();
    }

    @Then("cart should be empty")
    public void cart_should_be_empty() {
        Assert.assertTrue(cartPage.isCartEmpty(), "Empty cart message not displayed!");
    }

    @Then("user clicks here link if present")
    public void user_clicks_here_link_if_present() {
        cartPage.clickHereLinkIfPresent();
    }
}
