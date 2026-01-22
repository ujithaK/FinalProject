package api;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.testng.Assert;

public class ProductsAPITest {

    ProductsAPI productsApi = new ProductsAPI();

    @Test
    public void testGetAllProducts() {
        Response res = productsApi.getAllProducts();
        System.out.println(res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void testSearchProduct() {
        Response res = productsApi.searchProduct("tshirt");
        System.out.println(res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
    }
}
