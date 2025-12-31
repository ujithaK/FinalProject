package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(css = ".features_items .product-image-wrapper")
    private List<WebElement> products;

    @FindBy(id = "search_product")
    private WebElement searchBox;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(css = ".productinfo p")
    private List<WebElement> productNames;

    // Get total products displayed on UI
    public int getTotalProducts() {
        return products.size();
    }

    // Search product by name
    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchButton.click();
    }
}
