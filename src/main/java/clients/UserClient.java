package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends BurgerRestClient {

    private static final String USER_PATH = "/api/auth/";

    @Step("Creating user")
    public ValidatableResponse userCreate(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "register")
                .then().log().all();
    }

    @Step("Authorization user")
    public ValidatableResponse validation(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "login")
                .then().log().all();
    }

    @Step("Logout user")
    public ValidatableResponse logout(String refreshToken) {
        return given()
                .spec(getBaseSpec())
                .body(refreshToken)
                .when()
                .post(USER_PATH + "logout")
                .then().log().all();
    }

    @Step("Deleting user")
    public ValidatableResponse deletingUser(String accessToken, User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .auth().oauth2(accessToken.substring(7))
                .when()
                .delete(USER_PATH + "user")
                .then().log().all();
    }
}
