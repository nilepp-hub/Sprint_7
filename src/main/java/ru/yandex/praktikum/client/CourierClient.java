package ru.yandex.praktikum.client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.model.LoginForCourier;
import ru.yandex.praktikum.model.NewCourier;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.settings.Config.*;
import io.restassured.http.ContentType;

public class CourierClient {
    @Step("Регистрация нового курьера")
    public Response getCorrectNewCourier(NewCourier newCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(newCourier)
                .when()
                .post(COURIER);
        response.then();
        return  response;
    }

    @Step("Регистрация курьера с невалидными  данными")
    public Response getIncorrectCourier(NewCourier newCourier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(newCourier)
                .when()
                .post(COURIER);
    }

    @Step("Логин курьера")
    public Response login(LoginForCourier loginForCourier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .and()
                .body(loginForCourier)
                .when()
                .post(COURIER_LOGIN);
    }

    @Step("Удаление курьера")
    public Response deleteCourier(LoginForCourier loginForCourier) {
        Integer id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(loginForCourier)
                .post(COURIER_LOGIN)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");

        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .delete(COURIER + id);
    }
}