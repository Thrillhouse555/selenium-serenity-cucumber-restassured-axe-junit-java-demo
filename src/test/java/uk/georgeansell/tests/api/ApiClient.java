package uk.georgeansell.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL = "https://georgeansell.co.uk";
    private String authCookie;

    public ApiClient() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response login(String email, String password) {
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(Map.of("email", email, "password", password))
                .when()
                .post("/login")
                .then()
                .log().all()
                .extract()
                .response();

        // Store auth cookie if login successful
        if (response.statusCode() == 200) {
            String cookie = response.getCookie("token");
            if (cookie != null) {
                this.authCookie = cookie;
            }
        }

        return response;
    }

    public Response getProfile() {
        return authenticatedRequest()
                .log().all()
                .when()
                .get("/me")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getLinks() {
        return given()
                .log().all()
                .when()
                .get("/links")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getAllLinks() {
        return authenticatedRequest()
                .log().all()
                .queryParam("all", "true")
                .when()
                .get("/links")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response logout() {
        return authenticatedRequest()
                .log().all()
                .when()
                .post("/logout")
                .then()
                .log().all()
                .extract()
                .response();
    }

    private RequestSpecification authenticatedRequest() {
        RequestSpecification spec = given();
        if (authCookie != null) {
            spec.cookie("token", authCookie);
        }
        return spec;
    }

    public void setAuthCookie(String cookie) {
        this.authCookie = cookie;
    }

    public String getAuthCookie() {
        return authCookie;
    }
}
