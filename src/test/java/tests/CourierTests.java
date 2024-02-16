package tests;

import helpers.ResponseCodes;
import helpers.ResponseFields;
import helpers.Steps;
import helpers.TestData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierTests extends BaseTest {
    private final String login = TestData.generateLogin();
    private final String password = TestData.generatePassword();
    private final String creatingError = TestData.returnCreatingError();
    private final String notEnoughDataError = TestData.returnNotEnoughDataError();
    private final String emptyString = TestData.returnEmptyString();
    private final String fakeData = TestData.generateLogin();
    private final String notEnoughLoginDataError = TestData.returnNotEnoughLoginDataError();
    private final String accountNotFoundError = TestData.returnAccountNotFoundError();
    private int id;

    @Test
    @DisplayName("Create courier and check login")
    public void createCourierTest() {
        Response createCourier = Steps.createCourier(login, password);
        createCourier.then().body(ResponseFields.OK.getValue(), equalTo(true))
                .and()
                .statusCode(ResponseCodes.CREATED.getValue());
        id = Steps.getCourierId(Steps.loginCourier(login, password));
    }

    @Test
    @DisplayName("Create courier with already used login and password")
    public void createExistingCourierTest() {
        Steps.createCourier(login, password);
        Response response = Steps.createCourier(login, password);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(creatingError))
                .and()
                .statusCode(ResponseCodes.ALREADY_CREATED.getValue());
        id = Steps.getCourierId(Steps.loginCourier(login, password));
    }

    @Test
    @DisplayName("Create courier without password")
    public void createCourierWithoutPassword() {
        Response response = Steps.createCourier(login, emptyString);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(notEnoughDataError))
                .and()
                .statusCode(ResponseCodes.DENIED.getValue());
    }

    @Test
    @DisplayName("Create courier without login")
    public void createCourierWithoutLogin() {
        Response response = Steps.createCourier(emptyString, password);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(notEnoughDataError))
                .and()
                .statusCode(ResponseCodes.DENIED.getValue());
    }

    @Test
    @DisplayName("Login test")
    public void loginTest() {
        Steps.createCourier(login, password);
        Response response = Steps.loginCourier(login, password);
        response.then().body(ResponseFields.ID.getValue(), notNullValue())
                .and()
                .statusCode(ResponseCodes.OK.getValue());
        id = Steps.getCourierId(response);
    }

    @Test
    @DisplayName("Login with wrong login test")
    public void wrongLoginTest() {
        Steps.createCourier(login, password);
        Response response = Steps.loginCourierWrongData(emptyString, password);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(notEnoughLoginDataError))
                .and()
                .statusCode(ResponseCodes.DENIED.getValue());
        id = Steps.getCourierId(Steps.loginCourier(login, password));
    }

    @Test
    @DisplayName("Login with wrong password test")
    public void wrongPasswordTest() {
        Steps.createCourier(login, password);
        Response response = Steps.loginCourierWrongData(login, emptyString);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(notEnoughLoginDataError))
                .and()
                .statusCode(ResponseCodes.DENIED.getValue());
        id = Steps.getCourierId(Steps.loginCourier(login, password));
    }

    @Test
    @DisplayName("Login with non-existing data")
    public void nonExistingCourierLoginTest() {
        Steps.createCourier(login, password);
        Response response = Steps.loginCourierWrongData(fakeData, fakeData);
        response.then().body(ResponseFields.MESSAGE.getValue(), equalTo(accountNotFoundError))
                .and()
                .statusCode(ResponseCodes.NOT_FOUND.getValue());
        id = Steps.getCourierId(Steps.loginCourier(login, password));
    }

    @After
    public void tearDown() {
        Steps.deleteCourier(id);
    }

}
