import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Getting order list")
    @Description("This is the getting order list test with console output")

    public void canGetOrderList() {
        OrderCreation orderCreation = OrderCreation.getOrderData();
        given()
                .header("Content-type", "application/json")
                .and()
                .body(orderCreation)
                .when()
                .post("/api/v1/orders");
        given()
                .get("/api/v1/orders")
                .then().assertThat().body("orders", notNullValue());
    }
}
