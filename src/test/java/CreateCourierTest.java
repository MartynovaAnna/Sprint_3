import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest extends RestAssuredClient {
    private CourierClient courierClient;
    private int courierId;
    private static final String COURIER_PATH = "api/v1/courier/";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    @DisplayName("Check courier creation")
    @Description("This is a courier creation test with console output")

    public void courierCanBeCreatedWithValidData() {
        Courier courier = Courier.getRandom();
        Response response = createNewCourier(courier);
        courierId = CourierClient.login(CourierLoginPass.from(courier));
        response.then().assertThat().body("ok", equalTo(true));

        CourierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier")
    @Description("Basic test for /api/v1/courier")

    public void courierCreationCodeShouldBeCorrect() {
        Courier courier = Courier.getRandom();
        Response response = createNewCourier(courier);
        response.then().statusCode(201);

        courierId = CourierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check can't create two same couriers")
    @Description("Checking can't create two couriers with same login and password")

    public void canNotCreateTwoSameCouriers() {
        Courier courier = Courier.getRandom();
        createNewCourier(courier);
        Response response = createNewCourier(courier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
        courierId = CourierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check that login is necessary")
    @Description("Checking can't create courier without login")

    public void ifLoginFieldIsNecessary() {
        CourierWithoutLogin courierWithoutLogin = CourierWithoutLogin.withoutLogin();
        Response response = createNewCourierWithoutLogin(courierWithoutLogin);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check that password is necessary")
    @Description("Checking can't create courier without password")

    public void ifPasswordFieldIsNecessary() {
        CourierWithoutPassword courierWithoutPassword = CourierWithoutPassword.withoutPassword();
        Response response = createNewCourierWithoutPassword(courierWithoutPassword);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }


    @Step("Create new courier")
    public Response createNewCourier(Courier courier) {
        Response responce = given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(COURIER_PATH);
        return responce;
    }

    @Step("Create new courier without password")
    public Response createNewCourierWithoutPassword(CourierWithoutPassword courierWithoutPassword) {
        Response responce = given()
                .spec(getBaseSpecification())
                .body(courierWithoutPassword)
                .when()
                .post(COURIER_PATH);
        return responce;
    }

    @Step("Create new courier without login")
    public Response createNewCourierWithoutLogin(CourierWithoutLogin courierWithoutLogin) {
        Response responce = given()
                .spec(getBaseSpecification())
                .body(courierWithoutLogin)
                .when()
                .post(COURIER_PATH);
        return responce;
    }
}
