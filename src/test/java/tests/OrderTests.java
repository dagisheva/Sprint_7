package tests;

import helpers.Steps;
import helpers.TestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.OrderInfo;
import pojo.Orders;

import java.util.List;

public class OrderTests extends BaseTest {
    private final String someData = TestData.generateLogin();
    private final int daysNumber = TestData.chooseDaysNumber();
    private final String date = TestData.generateDate();
    private final List<String> color = TestData.chooseColor();
    private int track;
    private int courierId;
    private int orderId;

    @Test
    @DisplayName("Check order by courier test")
    public void checkOrderTest() {
        courierId = Steps.createCourierReturnId(someData, someData);
        track = Steps.createOrderReturnTrack(someData, someData, someData, someData, someData, date, someData, color,
                daysNumber);
        orderId = Steps.getOrderId(track);
        OrderInfo orderInfo = Steps.acceptOrderGetInfo(orderId, courierId);
        Orders order = orderInfo.getOrders().get(0);

        Assert.assertEquals("id заказа должен совпадать с ожидаемым", order.getId(), orderId);
        Assert.assertEquals("track должен совпадать с ожидаемым", order.getTrack(), track);
        Assert.assertEquals("id курьера должен совпадать с ожидаемым", order.getCourierId(), courierId);
    }

    @After
    public void tearDown() {
        Steps.deleteOrder(track);
    }
}
