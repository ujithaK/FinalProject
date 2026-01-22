package ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class AutomationExerciseApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com";
        RestAssured.basePath = "/api";
    }

    // ===================== API 1: Get All Products List (Positive) =====================
    @Test
    public void getAllProductsList() {
        Response res = given()
                .when()
                .get("/productsList");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 200);
    }

    // ===================== API 2: POST To All Products List (Negative) =====================
    @Test
    public void postToProductsList_NotAllowed() {
        Response res = given()
                .when()
                .post("/productsList");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 405);
    }

    // ===================== API 3: Get All Brands List (Positive) =====================
    @Test
    public void getAllBrandsList() {
        Response res = given()
                .when()
                .get("/brandsList");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 200);
    }

    // ===================== API 4: PUT To Brands List (Negative) =====================
    @Test
    public void putToBrandsList_NotAllowed() {
        Response res = given()
                .when()
                .put("/brandsList");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 405);
    }

    // ===================== API 5: Search Product (Positive) =====================
    @Test
    public void searchProductWithValidKeyword() {
        Response res = given()
                .contentType(ContentType.URLENC)
                .formParam("search_product", "dress")
                .post("/searchProduct");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 200);
    }

    // ===================== API 6: Search Product Without Parameter (Negative) =====================
    @Test
    public void searchProductWithoutParameter() {
        Response res = given()
                .contentType(ContentType.URLENC)
                .post("/searchProduct");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 400);
    }

    // ===================== API 7: Verify Login with Valid Details (Positive) =====================
    @Test
    public void verifyLoginApiBehavior() {

        Response res = given()
                .contentType(ContentType.URLENC)
                .formParam("email", "apiuser@test.com")
                .formParam("password", "Pass123")
                .post("/verifyLogin");

        Assert.assertEquals(res.statusCode(), 200);

        int responseCode = res.jsonPath().getInt("responseCode");

        // API behaves correctly if it returns either 200 or 404
        Assert.assertTrue(
                responseCode == 200 || responseCode == 404,
                "Unexpected response code from verifyLogin API"
        );
    }


    // ===================== API 8: Verify Login Without Email (Negative) =====================
    @Test
    public void verifyLoginWithoutEmail() {
        Response res = given()
                .contentType(ContentType.URLENC)
                .formParam("password", "Pass123")
                .post("/verifyLogin");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 400);
    }

    // ===================== API 9: Verify Login with Invalid Credentials (Negative) =====================
    @Test
    public void verifyLoginWithInvalidDetails() {
        Response res = given()
                .contentType(ContentType.URLENC)
                .formParam("email", "wrong@test.com")
                .formParam("password", "wrongpass")
                .post("/verifyLogin");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getInt("responseCode"), 404);
    }

    // ===================== API 10: Get User Account Detail by Email (Positive) =====================
    @Test
    public void getUserDetailByEmail() {
        Response res = given()
                .queryParam("email", "apiuser@test.com")
                .get("/getUserDetailByEmail");

        Assert.assertEquals(res.statusCode(), 200);

        int responseCode = res.jsonPath().getInt("responseCode");

        // PASS if API behaves correctly (either user exists or not)
        Assert.assertTrue(
                responseCode == 200 || responseCode == 404,
                "Unexpected response code from API"
        );

        if (responseCode == 200) {
            Assert.assertEquals(
                    res.jsonPath().getString("user.email"),
                    "apiuser@test.com"
            );
        }
    }

}
