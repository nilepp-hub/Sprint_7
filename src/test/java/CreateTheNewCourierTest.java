import ru.yandex.praktikum.client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.model.LoginForCourier;
import ru.yandex.praktikum.model.NewCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.testdata.CourierTestData;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateTheNewCourierTest {
    NewCourier newCourier;
    Integer id;
    CourierClient courierClient;
    private static final String EXPECTED_MESSAGE_400 = "Недостаточно данных для создания учетной записи";
    private static final String EXPECTED_MESSAGE_409 = "Этот логин уже используется. Попробуйте другой.";

    @Before
    public void setup() {
        newCourier = NewCourier.getCourierRequestAllRequiredField();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Невалидные данные для создания курьера: пустой login")
    @Description("Создание курьера с пустым полем login. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithoutLogin(){
        courierClient.getIncorrectCourier(CourierTestData.getCourierRequestWithoutLogin())
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_400));
    }

    @Test
    @DisplayName("Невалидные данные для создания курьера: пустой password")
    @Description("Создание курьера с пустым полем password. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithoutPassword(){
        courierClient.getIncorrectCourier(CourierTestData.getCourierRequestWithoutPassword())
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_400));
    }

    @Test
    @DisplayName("Невалидные данные для создания курьера: пустые login и password")
    @Description("Создание курьера с пустыми полями login и password. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для создания учетной записи\"")
    public void createCourierWithoutLoginAndPassword(){
        courierClient.getIncorrectCourier(CourierTestData.getCourierRequestWithoutLoginAndPassword())
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_400));
    }

    @Test
    @DisplayName("Валидные данные для создания курьера: login, password, firstName")
    @Description("Позитивный сценарий создания курьера. Ожидание: возвращается код ответа 201 и ok: true")
    public void createNewCorrectCourier() {
        courierClient.getCorrectNewCourier(CourierTestData.getCourierRequestAllRequiredField())
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Валидные данные для создания курьера: login, password")
    @Description("Позитивный сценарий создания курьера. Ожидание: возвращается код ответа 201 и ok: true")
    public void createWithoutFirstName() {
        courierClient.getCorrectNewCourier(CourierTestData.getCourierRequestWithoutFirstName())

                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание второго аналогичного курьера")
    @Description("При попытке создать курьера с повторяющимся логином ожидаем. Ожидание: возвращается код 409 и сообщение об ошибке \"Этот логин уже используется. Попробуйте другой.\"")
    public void createDuplicateCourier() {
        courierClient.getCorrectNewCourier(CourierTestData.getCourierRequestAllRequiredField());
        courierClient.getCorrectNewCourier(CourierTestData.getCourierRequestAllRequiredField())
                .then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo(EXPECTED_MESSAGE_409));
    }

    @After
    @Step("Удаление курьера")
    public void cleanUp() {
        if(id != null) {
            courierClient.deleteCourier(LoginForCourier.getLoginRequestAllRequiredField(CourierTestData.getCourierRequestWithoutFirstName()))
                    .then().statusCode(200);
            courierClient.deleteCourier(LoginForCourier.getLoginRequestAllRequiredField(CourierTestData.getCourierRequestAllRequiredField()))
                    .then().statusCode(200);
        }
    }
}
