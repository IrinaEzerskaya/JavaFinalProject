package ru.innopolis.demoqa;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.Assert;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import io.restassured.path.json.JsonPath;

public class AuthorizedAPITest extends AuthCredentials {

    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;

    @Test
    @Owner("IrinaEzerskaya")
    @Description("Тест авторизации в системе с валидными данными")
    @Given("Пользователь авторизовался")
    public void iAmAnAuthorizedUser() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        String payload = "{\"userName\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\"}";

        request.header("Content-Type","application/json");

        Response response = request
                .filter(new AllureRestAssured())
                .body(payload)
                .post("/Account/v1/GenerateToken");

        Assertions.assertEquals(200, response.getStatusCode());

        //response.prettyPrint();
        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));
        token = JsonPath.from(jsonString).get("token");

    }

    @Test
    @Owner("IrinaEzerskaya")
    @Description("Тест доступности книг к просмотру")
    @Given("Список книг доступен к просмотру для авторизованного пользователя")
    public void booksListIsAvailableForAuthUsers() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);

        bookId = books.get(0).get("isbn");
        //System.out.println("bookId: " + bookId);
    }

    @When("Добавляем книгу")
    public void bookAddTest() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"userId\": \"" + MYUSER_ID + "\", " +
                "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
                .post("/BookStore/v1/Books");
    }

    @Then("Книга добавлена")
    public void bookAddedResult() {
        //response.prettyPrint();
        Assert.assertEquals(201, response.getStatusCode());
    }

    @When("Удаляем книгу")
    public void bookDeleteTest() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + MYUSER_ID + "\"}")
                .delete("/BookStore/v1/Book");
    }

    @Then("Книга удалена")
    public void bookDeletedResult() {
        //response.prettyPrint();
        Assert.assertEquals(204, response.getStatusCode());
    }
}
