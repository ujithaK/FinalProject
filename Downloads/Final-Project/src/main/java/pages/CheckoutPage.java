package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class CheckoutPage {

    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    private By registerLoginBtn =
            By.xpath("//*[@id='checkoutModal']//a/u");

    public void clickRegisterOrLogin() {
        WebElement btn = driver.findElement(registerLoginBtn);
        WaitUtils.waitForElementClickable(btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
}
