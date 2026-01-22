package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By loginBtn = By.xpath("//button[contains(text(),'Login')]");

    public void login(String email, String password) {
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
}
