package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By proceedToCheckoutBtn =
            By.xpath("//a[contains(text(),'Proceed To Checkout')]");
    private By emptyCartMsg =
            By.xpath("//*[contains(text(),'Cart is empty')]");

    public void proceedToCheckout() {
        WebElement proceedBtn = driver.findElement(proceedToCheckoutBtn);
        WaitUtils.waitForElementClickable(proceedBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedBtn);
    }

    public boolean isCartEmpty() {
        WebElement msg = driver.findElement(emptyCartMsg);
        WaitUtils.waitForElementVisible(msg);
        return msg.isDisplayed();
    }

    public void clickHereLinkIfPresent() {
        try {
            WebElement hereLink =
                    driver.findElement(emptyCartMsg)
                            .findElement(By.xpath(".//a[contains(text(),'here')]"));
            WaitUtils.waitForElementClickable(hereLink);
            hereLink.click();
        } catch (NoSuchElementException ignored) {}
    }
}
