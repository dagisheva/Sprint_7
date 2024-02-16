package helpers;

public class ConfigData {
    private static final String courierPath = "/api/v1/courier";
    private static final String loginPath = "/login";
    private static final String orderPath = "/api/v1/orders";
    private static final String getOrder = "/track?t=";
    private static final String cancelOrderPath = "/cancel";
    private static final String acceptOrderPath = "/accept/";
    private static final String orderByCourierIdPath = "?courierId=";

    public static String returnCourierPath() {
        return courierPath;
    }

    public static String returnLoginPath() {
        return courierPath + loginPath;
    }

    public static String returnOrderPath() {
        return orderPath;
    }

    public static String returnOrderByTackPath() {
        return orderPath + getOrder;
    }

    public static String returnCancelOrderPath() {
        return orderPath + cancelOrderPath;
    }

    public static String returnAcceptOrderPath() {
        return orderPath + acceptOrderPath;
    }

    public static String returnOrderByCourierIdPath() {
        return orderPath + orderByCourierIdPath;
    }

}
