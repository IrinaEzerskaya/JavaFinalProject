package ru.innopolis.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

//@StartSelenoidEnvironment
@DisplayName("Анонимный UI тест")
public class AnonymousUITest extends AuthCredentials {

    private WebDriver webDriver;

    @Before
    public void setUp() {
        Configuration.driverManagerEnabled = false;
        Configuration.remote = SELENOID_ADDRESS;
    }

    @Test
    @Owner("IrinaEzerskaya")
    @Description("Тест авторизации в системе с не валидными данными")
    @Given("UI пользователь не авторизован")
    public void IncorrectCredentialsUser() {
        open(UIBASE_URL);
        this.webDriver = getWebDriver();
        this.webDriver.manage().window().maximize();
        Assertions.assertEquals($("#login").text(), "Login");
    }

    @Given("Список книг доступен к просмотру по UI")
    public void doesUIBookListExists() {
        // На главной странице должно быть несколько строк с книгами и тп
        ElementsCollection booksList = $$(byXpath("//div[contains(@class, 'rt-tr-group')]"));
        Assertions.assertTrue(booksList.size() > 0);
    }
    @Then("Список получен по UI")
    public void doesUIBookListReadable() {
        ElementsCollection booksNamesList = $$(byXpath("//div[contains(@class, 'action-buttons')]/span/a"));
        // На главной странице должно быть несколько ссылок на книги
        Assert.assertTrue(booksNamesList.size() > 0);
    }


}
