import io.qameta.allure.Step;

public class OrderCreation {

    public final String orderBody;

    public OrderCreation(String orderBody) {
        this.orderBody = orderBody;
    }

    @Step("Getting order data")
    public static OrderCreation getOrderData() {
        final String orderBody = "{\"firstName\": \"Naruto\", "
                + "\"lastName\": \"Uchiha\", "
                + "\"address\": \"Konoha, 142 apt.\", "
                + "\"metroStation\": 4, "
                + "\"phone\": \"+7 800 355 35 35\", "
                + "\"rentTime\": 5, "
                + "\"deliveryDate\": \"2020-06-06\", "
                + "\"comment\": \"Saske, come back to Konoha\", "
                + "\"color\": [\"BLACK\"]}";
        return new OrderCreation(orderBody);
    }
}
