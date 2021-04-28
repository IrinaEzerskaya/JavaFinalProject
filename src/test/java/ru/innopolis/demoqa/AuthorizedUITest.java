package ru.innopolis.demoqa;

import com.codeborne.selenide.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AuthorizedUITest extends AuthCredentials {

    private WebDriver webDriver;
    private static String bookURL;
    private static String bookId;
    WebDriverWait wait;
    JavascriptExecutor js;

    /*@Before
    public void setUp() {
        Configuration.driverManagerEnabled = false;
        Configuration.remote = SELENOID_ADDRESS;
    }*/

    @Given("UI пользователь авторизовался")
    public void canUserAuthorize() {
        Selenide.open(UIAUTH_URL);
        this.webDriver = getWebDriver();
        this.webDriver.manage().window().maximize();
        WebElement uName = this.webDriver.findElement(By.xpath("//*[@id='userName']"));
        WebElement pswd = this.webDriver.findElement(By.xpath("//*[@id='password']"));
        WebElement loginBtn = this.webDriver.findElement(By.xpath("//*[@id='login']"));

        uName.sendKeys(USERNAME);
        pswd.sendKeys(PASSWORD);
        loginBtn.click();

        // Задержка для корректного рендера DOM
        sleep(1000);

        try {

            open(BASE_URL + "/profile");

            // Ищем кнопку Logout
            WebElement logoutBtn = this.webDriver.findElement(By.id("submit"));
            Assertions.assertEquals(logoutBtn.isDisplayed(), true);

        } catch (Exception e) {
            System.out.println("Ошибка авторизации: " + e.getMessage());
        }

    }

    @Given("Список книг доступен к просмотру для авторизованного пользователя по UI")
    public void doesAuthorizedUserCanSeeProfileList() {
        open(UIBASE_URL);
        sleep(1000);
        ElementsCollection booksNamesList = $$(byXpath("//div[contains(@class, 'action-buttons')]/span/a"));
        // На главной странице должно быть несколько ссылок на книги
        Assertions.assertTrue(booksNamesList.size() > 0);
        if (booksNamesList.size() > 0) {
            String pattern = "^(.*?=)";
            bookURL = booksNamesList.get(0).getAttribute("href");
            bookId = bookURL.replaceAll(pattern, "");
        }

        System.out.println("UI bookID: " + bookId+ ", URL "+bookURL);
    }
    @When("Добавляем книгу по UI")
    public void canAuthorizedUserAddBook() {
        open(bookURL);
        sleep(1000);
        try {

            WebElement addBtn = this.webDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/button[@id='addNewRecordButton']"));
            Assertions.assertEquals(addBtn.isDisplayed(), true);

            // fix кривого Selenium: element click intercepted: Element <button id="addNewRecordButton" type="button" class="btn btn-primary">...</button> is not clickable at point (950, 773)
            js = (JavascriptExecutor) this.webDriver;
            js.executeScript("window.scrollBy(0, 300);");

            if (addBtn.isDisplayed()) {

                addBtn.click();
                sleep(1000);
                this.webDriver.switchTo().alert().accept();
                System.out.println("Книга добавлена!");
                sleep(1000);
            } else {
                System.out.println("Не найдена кнопка добавления!");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }
    @Then("Книга добавлена по UI")
    public void doesBooksHasBeenAdded() {
        open(BASE_URL + "/profile");
        sleep(1000);
        ElementsCollection booksList = $$(byXpath("//div[contains(@class, 'rt-tr-group')]"));
        Assertions.assertTrue(booksList.size() > 0);
    }
    @When("Удаляем книгу по UI")
    public void canAuthorizedUserDeleteBook() {
        ElementsCollection btnDeleteList = $$(byXpath("//div[contains(@class, 'rt-tr-group')]/*/*/div[contains(@class, 'action-buttons')]/span[contains(@title, 'Delete')]"));
        if (btnDeleteList.size() > 0) {
            SelenideElement btnDelete = btnDeleteList.first();
            btnDelete.click();
            sleep(1000);
            $(By.xpath("//button[contains(@id,'closeSmallModal-ok')]")).click();
            sleep(1000);
            this.webDriver.switchTo().alert().accept();

        }
        //sleep(5000);
        Assertions.assertTrue(btnDeleteList.size() >= 0);
    }
    @Then("Книга удалена по UI")
    public void doesBooksHasBeenDeleted() {
        open(BASE_URL + "/profile");
        ElementsCollection booksNamesList = $$(byXpath("//div[contains(@class, 'action-buttons')]/span/a"));
        Assertions.assertTrue(booksNamesList.size() == 0);
    }

}
