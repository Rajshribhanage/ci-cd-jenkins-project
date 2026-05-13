package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiLoginTest {

    @Test
    public void validLoginTest() {

        String requestBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        Response response =
                RestAssured
                        .given()
                        .baseUri("https://reqres.in")
                        .basePath("/api/login")
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post()
                        .then()
                        .extract().response();

        // Print response (for debugging)
        System.out.println("Response Body: " + response.asString());

        // Validate status code
        Assert.assertEquals(response.getStatusCode(), 200);

        // Validate token exists
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test
    public void invalidLoginTest() {

        String requestBody = "{\n" +
                "    \"email\": \"wrong@example.com\"\n" +
                "}";

        Response response =
                RestAssured
                        .given()
                        .baseUri("https://reqres.in")
                        .basePath("/api/login")
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post();

        System.out.println("Response Body: " + response.asString());

        // ReqRes returns 400 for invalid login
        Assert.assertEquals(response.getStatusCode(), 400);

        // Validate error message
        String error = response.jsonPath().getString("error");
        Assert.assertEquals(error, "Missing password");
    }
}