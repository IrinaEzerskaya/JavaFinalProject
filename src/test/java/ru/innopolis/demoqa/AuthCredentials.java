package ru.innopolis.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

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

    // Для тестирования UI на Chrome v.88
    public static class CustomWebDriverProvider implements WebDriverProvider {
        @Override
        public WebDriver createDriver(DesiredCapabilities capabilities) {
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("browserVersion", "88.0");
            // java: cannot find symbol
            //  symbol:   method <java.lang.String,java.lang.Object>of(java.lang.String,boolean,java.lang.String,boolean)
            /*capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));*/
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            try {
                System.out.println("Connect to remote " + SELENOID_ADDRESS);
                return new RemoteWebDriver(new URL(SELENOID_ADDRESS), capabilities);
            } catch (final MalformedURLException e) {
                throw new RuntimeException("Unable to create driver", e);
            }
        }
    }

}
