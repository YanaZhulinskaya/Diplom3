package com.stellarburgertest;

import com.stellarburger.User;
import io.qameta.allure.junit4.DisplayName;
import com.stellarburger.pageobject.AuthorizationPage;
import com.stellarburger.pageobject.MainPage;
import com.stellarburger.pageobject.RegistrationPage;
import com.stellarburger.pageobject.RestorePasswordPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.assertEquals;

public class AuthorizationTest {
    private RegistrationPage objRegistrationPage;
    private AuthorizationPage objAuthorizationPage;
    private WebDriver driver;
    private String name;
    private String email;
    private String password;

    @Before
    public void before() {
        // тест для  Chrome Browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        // for testing in Yandex Browser
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
       // ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Yandex.app/Contents/MacOS/Yandex");
        driver = new ChromeDriver(options);
        User user = new User();
        name = user.getRandomName();
        email = user.getRandomEmail();
        password = user.getRandomPassword();
        objRegistrationPage = new RegistrationPage(driver);
        objRegistrationPage.openRegPage();
        objRegistrationPage.createNewUser(name,email,password);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    @DisplayName("check Authorization path by Authorization Button at MainPage")
    public void mainPageAuthorizationButtonTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.clickAuthorizationButton();
        objAuthorizationPage = new AuthorizationPage(driver);
        objAuthorizationPage.authorization(email, password);
        Assert.assertEquals("Authorization was Failed", "Оформить заказ", objMainPage.textOrderButton());
    }

    @Test
    @DisplayName("Check authorization path by Personal Account button at Main page")
    public void authorizationByPersonalAccountTest() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openMainPage();
        objMainPage.clickUserAccountButton();
        objAuthorizationPage = new AuthorizationPage(driver);
        objAuthorizationPage.authorization(email,password);
        Assert.assertEquals("Authorization was Failed", "Оформить заказ", objMainPage.textOrderButton());
    }

    @Test
    @DisplayName("check Authorization path by Authorization Link at Registration Page")
    public void authorizationByRegistrationLinkTest() {
        objRegistrationPage.openRegPage();
        objRegistrationPage.clickAuthLinkByRegForm();
        AuthorizationPage objAuthorizationPage = new AuthorizationPage(driver);
        objAuthorizationPage.authorization(email,password);
        MainPage objMainPage = new MainPage(driver);
        assertEquals("Authorization was Failed", "Оформить заказ", objMainPage.textOrderButton());
    }

    @Test
    @DisplayName("check Authorization path by Authorization Link at ResetPassword Page")
    public void authorizationByRestorePasswordLinkTest() {
        RestorePasswordPage objRestorePasswordPage = new RestorePasswordPage(driver);
        objRestorePasswordPage.openRestorePage();
        objRestorePasswordPage.clickAuthLinkByRestorePassForm();
        AuthorizationPage objAuthorizationPage = new AuthorizationPage(driver);
        objAuthorizationPage.authorization(email,password);
        MainPage objMainPage = new MainPage(driver);
        assertEquals("Authorization was Failed", "Оформить заказ", objMainPage.textOrderButton());
    }
}

