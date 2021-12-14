import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest extends RestAssuredClient {

    private static final String ORDER_LIST = "/api/v1/orders/";

    @Test
    @DisplayName("Getting order list")
    @Description("This is the getting order list test with console output")

    public void canGetOrderList() {
        OrderData orderCreation = OrderData.getRandom();
        createMewOrder(orderCreation);
        Response response = gettingOrderList();
        response.then().assertThat().body("orders", notNullValue());
    }

    @Step("Create new order")
    public Response createMewOrder(OrderData orderCreation) {
        Response response = given()
                .spec(getBaseSpecification())
                .and()
                .body(orderCreation)
                .when()
                .post(ORDER_LIST);
        return response;
    }

    @Step("Get order list")
    public Response gettingOrderList() {
        Response response = given()
                .spec(getBaseSpecification())
                .when()
                .get(ORDER_LIST);
        return response;
    }
}
