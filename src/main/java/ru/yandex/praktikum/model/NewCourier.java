package ru.yandex.praktikum.model;
import org.apache.commons.lang3.RandomStringUtils;

public class NewCourier {
    private String login;
    private String password;
    private String firstName;

    public NewCourier() {
    }

    public NewCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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

    public static NewCourier getCourierRequestWithoutFirstName()
    {
        NewCourier newCourier = new NewCourier();
        newCourier.setLogin(LOGIN);
        newCourier.setPassword(PASSWORD);
        return newCourier;
    }
}
