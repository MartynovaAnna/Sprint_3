import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest extends RestAssuredClient {
    private CourierClient courierClient;
    private int courierId;
    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String LOGIN_PATH = "api/v1/courier/login/";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check courier login")
    @Description("This is a courier creation test with console output")

    public void courierCanLogIn() {
        Courier courier = Courier.getRandom();
        CourierLoginPass logPass = CourierLoginPass.from(courier);

        createNewCourier(courier);
        Response response = courierLogin(logPass);
        response.then().assertThat().body("id", is(not(0)));
        courierId = CourierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Check status code of /api/v1/courier/login")
    @Description("Basic test for /api/v1/courier/login")

    public void courierShouldLogInWithRightCode() {
        Courier courier = Courier.getRandom();
        CourierLoginPass logPass = CourierLoginPass.from(courier);

        createNewCourier(courier);
        Response response = courierLogin(logPass);
        response.then().statusCode(200);
        courierId = CourierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check can't log in without login")
    @Description("Checking that courier can't login without login")

    public void canNotLogInWithoutLogin() {
        CourierWithoutFirstNameAndLogin courier = CourierWithoutFirstNameAndLogin.withoutFirstNameAndLogin();
        Response response = CourierWithoutFirstNameAndLogin(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check can't log in without password")
    @Description("Checking that courier can't login without password")

    public void canNotLogInWithoutPassword() {
        CourierWithoutFirstNameAndPassword courier = CourierWithoutFirstNameAndPassword.withoutFirstNameAndPassword();
        Response response = CourierWithoutFirstNameAndPassword(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check can't login with wrong password")
    @Description("Checking that courier can't login wrong password")

    public void canNotLoginWithWrongPassword() {
        CourierWithWrongPassword courier = CourierWithWrongPassword.withWrongPassword();
        createCourierWithWrongPassword(courier);
        CourierWithWrongPassword wrongPassCourier = CourierWithWrongPassword.withWrongPassword();
        Response response = CourierWithWrongPasswordLogin(wrongPassCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
            .and()
                .statusCode(404);
        courierId = CourierClient.loginWithWrongPass(CourierLoginPass.from(courier));
        courierClient.deleteWithWrongPass(courierId);
}

    @Test
    @DisplayName("Check can't login with wrong login")
    @Description("Checking that courier can't login wrong login")

    public void canNotLoginWithWrongLogin() {
        Courier courier = Courier.getRandom();
        createNewCourier(courier);

        Courier courierWithWrongLogin = Courier.getRandom();
        CourierLoginPass logPassWrongLogin = CourierLoginPass.from(courierWithWrongLogin);

        Response response = courierLogin(logPassWrongLogin);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierId = CourierClient.login(CourierLoginPass.from(courier));
        courierClient.delete(courierId);
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

    @Step("Create new courier with wrong Password")
    public Response createCourierWithWrongPassword(CourierWithWrongPassword courierWithWrongPassword) {
        Response responce = given()
                .spec(getBaseSpecification())
                .body(courierWithWrongPassword)
                .when()
                .post(COURIER_PATH);
        return responce;
    }

    @Step("Courier login")
    public Response courierLogin(CourierLoginPass logPass) {
        return given()
                .spec(getBaseSpecification())
                .body(logPass)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Create new courier without first name and login")
    public Response CourierWithoutFirstNameAndLogin(CourierWithoutFirstNameAndLogin courier) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    @Step("Create new courier without first name and password")
    public Response CourierWithoutFirstNameAndPassword(CourierWithoutFirstNameAndPassword courier) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    @Step("Login courier with wrong Password")
    public Response CourierWithWrongPasswordLogin(CourierWithWrongPassword courier) {
        Response response = given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(LOGIN_PATH);
        return response;
    }
}
