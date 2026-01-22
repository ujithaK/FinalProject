package api;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.testng.Assert;

public class UserAPITest extends BaseAPITest {

    @Test
    public void testValidLogin() {
        String jsonBody = "{ \"email\": \"test@example.com\", \"password\": \"password123\" }";
        Response res = postRequest("/verifyLogin", jsonBody);
        System.out.println(res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void testInvalidLogin() {
        String jsonBody = "{ \"email\": \"invalid@example.com\", \"password\": \"wrongpass\" }";
        Response res = postRequest("/verifyLogin", jsonBody);
        System.out.println(res.asPrettyString());
        Assert.assertEquals(res.getStatusCode(), 200); // API returns 200 even for invalid credentials, check response content
    }
}
