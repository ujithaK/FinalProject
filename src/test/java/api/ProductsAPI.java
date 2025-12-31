package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class ProductsAPI {

    private static final String BASE_URL = "https://automationexercise.com/api";

    // GET all products
    public Response getAllProducts() {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .get("/productsList")
                .then()
                .extract()
                .response();
    }

    // POST search product
    public Response searchProduct(String productName) {
        String jsonBody = "{ \"search_product\": \"" + productName + "\" }";
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/searchProduct")
                .then()
                .extract()
                .response();
    }
}
