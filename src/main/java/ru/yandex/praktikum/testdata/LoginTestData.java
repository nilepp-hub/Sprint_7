package ru.yandex.praktikum.testdata;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.model.NewCourier;
import ru.yandex.praktikum.model.LoginForCourier;

public class LoginTestData {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(10);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(10);
    public static LoginForCourier correctLogin(NewCourier newCourier) {
        LoginForCourier loginForCourier = new LoginForCourier();
        loginForCourier.setLogin(newCourier.getLogin());
        loginForCourier.setPassword(newCourier.getPassword());
        return loginForCourier;
    }
    public static LoginForCourier invalidLoginPassword() {
        LoginForCourier loginForCourier = new LoginForCourier();
        loginForCourier.setLogin(LOGIN);
        loginForCourier.setPassword(PASSWORD);
        return loginForCourier;
    }
    public static LoginForCourier requestWithoutLogin() {
        LoginForCourier loginForCourier = new LoginForCourier();
        loginForCourier.setPassword(PASSWORD);
        return loginForCourier;
    }
}
