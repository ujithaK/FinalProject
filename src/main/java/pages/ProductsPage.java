package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class ProductsPage {

    private WebDriver driver;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By firstAddToCartBtn =
            By.xpath("(//a[contains(text(),'Add to cart')])[1]");
    private By cartModal = By.id("cartModal");
    private By viewCartBtn = By.xpath("//a[@href='/view_cart']");
    private By adFrame = By.id("aswift_3");

    public void hideAdIfPresent() {
        try {
            WebElement ad = driver.findElement(adFrame);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.display='none';", ad);
        } catch (Exception ignored) {}
    }

    public void addFirstProductToCart() {
        WebElement addBtn = driver.findElement(firstAddToCartBtn);
        WaitUtils.waitForElementClickable(addBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
    }

    public void waitForCartPopup() {
        WebElement popup = driver.findElement(cartModal);
        WaitUtils.waitForElementVisible(popup);
    }

    public void clickViewCart() {
        WebElement viewCart = driver.findElement(viewCartBtn);
        WaitUtils.waitForElementClickable(viewCart);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCart);
    }
}
