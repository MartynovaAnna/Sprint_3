import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check courier creation")
    @Description("This is a courier creation test with console output")

    public void courierCanBeCreatedWithValidData() {
        Courier courier = Courier.getRandom();

        boolean isCourierCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierLoginPass.from(courier));

        assertTrue("Courier is not created", isCourierCreated);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier")
    @Description("Basic test for /api/v1/courier")

    public void courierCreationCodeShouldBeCorrect() {
        Courier courier = Courier.getRandom();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(201);

        courierId = courierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check can't create two same couriers")
    @Description("Checking can't create two couriers with same login and password")

    public void canNotCreateTwoSameCouriers() {
        Courier courier = Courier.getRandom();
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier");
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
        courierId = courierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check that login is necessary")
    @Description("Checking can't create courier without login")

    public void ifLoginFieldIsNecessary() {
        Courier courier = Courier.withoutLogin();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check that password is necessary")
    @Description("Checking can't create courier without password")

    public void ifPasswordFieldIsNecessary() {
        Courier withoutPassword = Courier.withoutPassword();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(withoutPassword)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
