import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check courier login")
    @Description("This is a courier creation test with console output")

    public void courierCanLogIn() {
        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertThat("Courier ID is incorrect", courierId, is(not(0)));

        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Check status code of /api/v1/courier/login")
    @Description("Basic test for /api/v1/courier/login")

    public void courierShouldLogInWithRightCode() {
        Courier courier = Courier.withoutFirstName();
        courierClient.create(courier);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(200);
        courierId = courierClient.login(CourierCredentials.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check can't log in without login")
    @Description("Checking that courier can't login without login")

    public void canNotLogInWithoutLogin() {
        Courier courier = Courier.withoutFirstNameAndLogin();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check can't log in without password")
    @Description("Checking that courier can't login without password")

    public void canNotLogInWithoutPassword() {
        Courier courier = Courier.withoutFirstNameAndPassword();
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check can't login with wrong password")
    @Description("Checking that courier can't login wrong password")

    public void canNotLoginWithWrongPassword() {
        Courier courier = Courier.withWrongPassword();
        Courier courierWithWrongPass = Courier.withWrongPassword();
        courierClient.create(courier);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(courierWithWrongPass)
            .when()
            .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
            .and()
                .statusCode(404);
        courierId = courierClient.login(CourierCredentials.from(courier));
        courierClient.delete(courierId);
}

    @Test
    @DisplayName("Check can't login with wrong login")
    @Description("Checking that courier can't login wrong login")

    public void canNotLoginWithWrongLogin() {
        Courier courier = Courier.withoutFirstName();
        Courier courierWithWrongLogin = Courier.withoutFirstName();
        courierClient.create(courier);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithWrongLogin)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierId = courierClient.login(CourierCredentials.from(courier));
        courierClient.delete(courierId);
    }
}
