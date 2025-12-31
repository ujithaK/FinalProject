package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("user launches the application")
    public void user_launches_the_application() {
        // Initialize WebDriver using the singleton DriverFactory
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);

//         Navigate to login page
//        loginPage.navigateTo("https://automationexercise.com/login");
    }

    @When("user enters email {string} and password {string}")
    public void user_enters_email_and_password(String email, String password) {
        // Perform login using LoginPage method
        loginPage.login(email, password);
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        // Verify that the Logout button is displayed
        boolean isLogoutVisible = isLogoutButtonDisplayed();
        Assert.assertTrue(isLogoutVisible, "Logout button is not displayed! Login might have failed.");
    }

    // Helper method to check logout button visibility
    private boolean isLogoutButtonDisplayed() {
        try {
            return driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
