package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class AutomationExerciseApiSteps {

    Response response;

    @Given("the Automation Exercise API is available")
    public void setupApi() {
        RestAssured.baseURI = "https://automationexercise.com";
        RestAssured.basePath = "/api";
    }

    // ===================== COMMON REQUESTS =====================

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = given().get(endpoint);
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        response = given().post(endpoint);
    }

    @When("I send a PUT request to {string}")
    public void sendPutRequest(String endpoint) {
        response = given().put(endpoint);
    }

    @Then("the API response code should be {int}")
    public void validateResponseCode(int expectedCode) {
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), expectedCode);
    }

    // ===================== SEARCH =====================

    @When("I search product with keyword {string}")
    public void searchProductWithKeyword(String keyword) {
        response = given()
                .contentType(ContentType.URLENC)
                .formParam("search_product", keyword)
                .post("/searchProduct");
    }

    @When("I search product without keyword")
    public void searchProductWithoutKeyword() {
        response = given()
                .contentType(ContentType.URLENC)
                .post("/searchProduct");
    }

    // ===================== LOGIN =====================

    @When("I verify login using email {string} and password {string}")
    public void verifyLogin(String email, String password) {
        response = given()
                .contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password", password)
                .post("/verifyLogin");
    }

    @When("I verify login without email")
    public void verifyLoginWithoutEmail() {
        response = given()
                .contentType(ContentType.URLENC)
                .formParam("password", "Pass123")
                .post("/verifyLogin");
    }

    @Then("the login API should respond correctly")
    public void validateLoginBehavior() {
        Assert.assertEquals(response.statusCode(), 200);

        int responseCode = response.jsonPath().getInt("responseCode");
        Assert.assertTrue(
                responseCode == 200 || responseCode == 404,
                "Unexpected response code for login API"
        );
    }

    // ===================== USER DETAILS =====================

    @When("I get user details for email {string}")
    public void getUserDetails(String email) {
        response = given()
                .queryParam("email", email)
                .get("/getUserDetailByEmail");
    }

    @Then("the user details API should respond correctly")
    public void validateUserDetailsResponse() {
        Assert.assertEquals(response.statusCode(), 200);

        int responseCode = response.jsonPath().getInt("responseCode");
        Assert.assertTrue(
                responseCode == 200 || responseCode == 404,
                "Unexpected response code for user details API"
        );

        if (responseCode == 200) {
            Assert.assertNotNull(response.jsonPath().getString("user.email"));
        }
    }
}
