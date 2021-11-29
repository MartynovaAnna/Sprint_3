import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Create new courier")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Courier login")
    public int login(CourierLoginPass credentials) {
        return given()
                .spec(getBaseSpecification())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Delete courier")
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpecification())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
