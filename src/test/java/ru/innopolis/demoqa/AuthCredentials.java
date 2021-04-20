package ru.innopolis.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.*;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AuthCredentials {

    // Url удалённого веб драйвера
    protected static final String SELENOID_ADDRESS = "http://188.130.155.80:4444/wd/hub";
    // Url магазина
    protected static final String UIBASE_URL = "https://demoqa.com/books";
    // Url страницы авторизации
    protected static final String UIAUTH_URL = "https://demoqa.com/login";

    protected static final String MYUSER_ID = "ac29b268-1ec8-4ece-968d-d0a9c4a0defd";
    protected static final String USERNAME = "QAZwsx";
    protected static final String PASSWORD = "Qq123123!";
    protected static final String BASE_URL = "https://demoqa.com";

}
