package ru.innopolis.demoqa;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;


public class AnonymousAPITest {

    private static final String BASE_URL = "https://demoqa.com";

    private static Response response;
    private static String jsonString;
    private static String bookId;

    @Test
    @Owner("IrinaEzerskaya")
    @Description("Тест авторизации в системе с не валидными данными")
    @Given("Пользователь не авторизован")
    public void IncorrectCredentialsUser() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String payload = "{\"userName\": \"??\", \"password\": \"???\"}";

        request.header("Content-Type","application/json");

        Response response = request
                .filter(new AllureRestAssured())
                .body(payload)
                .post("/Account/v1/GenerateToken");

        Assertions.assertEquals(200, response.getStatusCode());

        //response.prettyPrint();
        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("authorization failed"));

    }

    @Test
    @Given("Список книг доступен к просмотру")
    public void checkBookList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);

        bookId = books.get(0).get("isbn");
    }

    @When("Просматриваем список")
    public void requestBookList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");
    }
    @Then("Список получен")
    public void getBookListResponse() {
        Assert.assertEquals(200, response.getStatusCode());
    }

}
