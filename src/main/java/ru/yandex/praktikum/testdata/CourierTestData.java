package ru.yandex.praktikum.testdata;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.model.NewCourier;

public class CourierTestData {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(10);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(10);
    private static final String FIRST_NAME = "bulgur";
    public static NewCourier getCourierRequestAllRequiredField()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setLogin(LOGIN);
        newCourier.setPassword(PASSWORD);
        newCourier.setFirstName(FIRST_NAME);
        return newCourier;
    }
    public static NewCourier getCourierRequestWithoutLogin()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setPassword(PASSWORD);
        newCourier.setFirstName(FIRST_NAME);
        return newCourier;
    }
    public static NewCourier getCourierRequestWithoutPassword()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setLogin(LOGIN);
        newCourier.setFirstName(FIRST_NAME);
        return newCourier;
    }
    public static NewCourier getCourierRequestWithoutLoginAndPassword()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setFirstName(FIRST_NAME);
        return newCourier;
    }
    public static NewCourier getCourierRequestWithoutFirstName()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setLogin(LOGIN + "1");
        newCourier.setPassword(PASSWORD + "1");
        return newCourier;
    }
}
