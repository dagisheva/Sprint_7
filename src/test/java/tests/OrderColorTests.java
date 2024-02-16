package tests;

import helpers.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.OrderInfo;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderColorTests extends BaseTest {
    private final String someData = TestData.generateLogin();
    private final int daysNumber = TestData.chooseDaysNumber();
    private final String date = TestData.generateDate();
    private final List<String> color;
    private int track;

    public OrderColorTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] listOfColors() {
        return new Object[][]{
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of("BLACK", "GREY")},
                {List.of("")},
        };
    }

    @Test
    @DisplayName("Colors parameterized test")
    public void colorsTest() {
        Response response =
                Steps.createOrder(someData, someData, someData, someData, someData, date, someData, color, daysNumber);
        response.then().body(ResponseFields.TRACK.getValue(), notNullValue())
                .and()
                .statusCode(ResponseCodes.CREATED.getValue());
        track = Steps.getOrderTrack(response);
        OrderInfo orderReturn = Steps.getOrderByTrack(track);
        List<String> actualColor = orderReturn.getOrder().getColor();
        Assert.assertEquals("Цвета должны совпадать", actualColor, color);
    }

    @After
    public void tearDown() {
        Steps.deleteOrder(track);
    }
}
