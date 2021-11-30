import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Extract courier id")
    public static int login(CourierLoginPass credentials) {
        return given()
                .spec(getBaseSpecification())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then()
                .extract()
                .path("id");
    }

    @Step("Extract courier id with wrong pass")
    public static int loginWithWrongPass(CourierLoginPass credentials) {
        return given()
                .spec(getBaseSpecification())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then()
                .extract()
                .path("id");
    }

    @Step("Delete courier")
    public static boolean delete(int courierId) {
        return given()
                .spec(getBaseSpecification())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .extract()
                .path("ok");
    }

    @Step("Delete courier with wrong password")
    public static boolean deleteWithWrongPass(int courierId) {
        return given()
                .spec(getBaseSpecification())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .extract()
                .path("ok");
    }
}
