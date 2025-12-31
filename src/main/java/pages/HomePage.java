package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "a[href='/products']")
    private WebElement productsLink;

    public void goToProductsPage() {
        productsLink.click();
    }
}
