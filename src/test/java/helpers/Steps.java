package helpers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Courier;
import pojo.Order;
import pojo.OrderInfo;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Steps {
    private static final String courierPath = ConfigData.returnCourierPath();
    private static final String loginPath = ConfigData.returnLoginPath();
    private static final String orderPath = ConfigData.returnOrderPath();
    private static final String getOrderByTrackPath = ConfigData.returnOrderByTackPath();
    private static final String cancelOrderPath = ConfigData.returnCancelOrderPath();
    private static final String acceptOrderPath = ConfigData.returnAcceptOrderPath();
    private static final String orderByCourierIdPath = ConfigData.returnOrderByCourierIdPath();
    private static Order order;
    private static Courier courier;

    @Step("Create courier")
    public static Response createCourier(String login, String password) {
        courier = new Courier(login, password);
        return given().header("Content-type", "application/json").and().body(courier).when().post(courierPath);
    }

    @Step("Login courier")
    public static Response loginCourier(String login, String password) {
        Courier loginCourier = new Courier(login, password);
        return given().header("Content-type", "application/json").and().body(loginCourier).when().post(loginPath);
    }

    @Step("Login courier wrong data")
    public static Response loginCourierWrongData(String login, String password) {
        Courier loginCourier = new Courier(login, password);
        Response response = given().header("Content-type", "application/json").and().body(loginCourier).when().post(loginPath);
        return response;
    }

    @Step("Get courier id")
    public static int getCourierId(Response response) {
        return response.jsonPath().getInt(ResponseFields.ID.getValue());
    }

    @Step("Create courier and return courierId")
    public static int createCourierReturnId(String login, String password) {
        createCourier(login, password);
        Response response = loginCourier(login, password);
        return getCourierId(response);
    }

    @Step("Delete courier")
    public static Response deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(id)
                .when()
                .delete(courierPath + "/" + id);
    }

    @Step("Create Order")
    public static Response createOrder(String firstName, String lastName, String address, String metroStation,
                                       String phone, String deliveryDate, String comment, List<String> color,
                                       int daysNumber) {
        order = new Order(firstName, lastName, address, metroStation, phone, deliveryDate, comment, color, daysNumber);
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(orderPath);
    }

    @Step("Create order and return track")
    public static int createOrderReturnTrack(String firstName, String lastName, String address, String metroStation,
                                             String phone, String deliveryDate, String comment, List<String> color,
                                             int daysNumber) {
        Response response = createOrder(firstName, lastName, address, metroStation,
                phone, deliveryDate, comment, color, daysNumber);
        return getOrderTrack(response);
    }

    @Step("Get order track")
    public static int getOrderTrack(Response response) {
        return response.jsonPath().getInt(ResponseFields.TRACK.getValue());
    }

    @Step("Get order by track")
    public static OrderInfo getOrderByTrack(int track) {
        return given()
                .header("Content-type", "application/json")
                .get(getOrderByTrackPath + track)
                .body()
                .as(OrderInfo.class);
    }

    @Step("Delete order")
    public static Response deleteOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(track)
                .when()
                .delete(cancelOrderPath);
    }

    @Step("Accept order")
    public static Response acceptOrder(int orderId, int courierId) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .put(acceptOrderPath + orderId + "?courierId=" + courierId);
    }

    @Step("Check order list by courier")
    public static OrderInfo checkOrderListByCourierId(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .get(orderByCourierIdPath + courierId)
                .body()
                .as(OrderInfo.class);
    }

    @Step("Accept order and get it")
    public static OrderInfo acceptOrderGetInfo(int orderId, int courierId) {
        acceptOrder(orderId, courierId);
        return checkOrderListByCourierId(courierId);
    }

    @Step("Get orders")
    public static Response getOrders(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(orderByCourierIdPath + courierId);
    }

    @Step("Get order id")
    public static int getOrderId(int track) {
        return getOrderByTrack(track).getOrder().getId();
    }
}

