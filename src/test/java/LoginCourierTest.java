import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import ru.yandex.praktikum.model.NewCourier;
import ru.yandex.praktikum.model.LoginForCourier;
import ru.yandex.praktikum.testdata.CourierTestData;
import ru.yandex.praktikum.testdata.LoginTestData;
import static ru.yandex.praktikum.settings.Config.getBaseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.testdata.CourierTestData.getCourierRequestAllRequiredField;
import static ru.yandex.praktikum.testdata.LoginTestData.invalidLoginPassword;
import static ru.yandex.praktikum.testdata.LoginTestData.requestWithoutLogin;

public class LoginCourierTest {
    public static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER = "courier";

    @AfterClass
    public static void setId() {

        int id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(getCourierRequestAllRequiredField())
                .post("courier/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .delete("courier/" + id);
    }

    @Test
    @DisplayName("Авторизация курьера с корректным логином и паролем")
    @Description("Проверка возможности успешно авторизоваться с валидным логином и паролем. Ожидаемый результат: код 200 и возвращается ID")
    public void courierAuthWithCorrectPasswordLogin() {
        NewCourier newCourier = getCourierRequestAllRequiredField();

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(newCourier)
                .post(COURIER);

        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(getCourierRequestAllRequiredField())
                .post(COURIER_LOGIN);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера с невалидным логином")
    @Description("Проверка авторизации с невалидным логином. Ожидаемый результат: код ответа 404 и сообщение об ошибке \"Учетная запись не найдена\"")
    public void courierAuthWithNonexistentLogin() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(invalidLoginPassword())
                .post(COURIER_LOGIN);
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина или пароля")
    @Description("Проверяем авторизацию с незаполненным логином или паролем. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для входа\"")
    public void courierAuthWithoutRequiredField() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(requestWithoutLogin())
                .post(COURIER_LOGIN);
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}