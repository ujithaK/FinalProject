package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class BaseAPITest {
    // Base URL
    public static final String BASE_URL = "https://automationexercise.com/api";

    // Helper method for GET request
    public Response getRequest(String endpoint) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    // Helper method for POST request with JSON body
    public Response postRequest(String endpoint, String jsonBody) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
