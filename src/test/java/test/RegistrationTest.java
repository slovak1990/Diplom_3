package test;

import clients.User;
import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.Assert.assertTrue;
import static pages.LoginPage.LOGIN_PAGE_URL;

@Epic("Creating new user role")
@Feature("Registration")
public class RegistrationTest{

    private User user;
    private UserClient userClient;
    private MainPage mainPage;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        mainPage = open(MainPage.MAIN_PAGE_URL, MainPage.class);
    }

    @After
    public void tearDown() {
        userClient.deletingUser(accessToken, user);
    }

    @Test
    @DisplayName("Positive registration")
    @Description("Positive registration and check LOGIN_PAGE_URL")
    public void positiveRegistrationTest() {
        mainPage.clickEnterAccountButton();

        LoginPage loginPage = page(LoginPage.class);
        loginPage.clickRegistrationButton();

        RegistrationPage registrationPage = page(RegistrationPage.class);
        registrationPage.setNameField(user.getName());
        registrationPage.setEmailField(user.getEmail());
        registrationPage.setPasswordField(user.getPassword());
        registrationPage.clickRegistrationButton();

        webdriver().shouldHave(url(LOGIN_PAGE_URL));
    }

    @Test
    @DisplayName("Negative registration")
    @Description("Check error field for password {<5}")
    public void checkErrorFieldPasswordTest() {
        mainPage.clickEnterAccountButton();

        LoginPage loginPage = page(LoginPage.class);
        loginPage.clickRegistrationButton();

        RegistrationPage registrationPage = page(RegistrationPage.class);
        registrationPage.setNameField(user.getName());
        registrationPage.setEmailField(user.getEmail());
        registrationPage.setPasswordField(RandomStringUtils.randomAlphabetic(5));
        registrationPage.clickRegistrationButton();

        assertTrue("Error password field not displayed", registrationPage.errorPasswordFieldIsDisplayed());
    }
}
