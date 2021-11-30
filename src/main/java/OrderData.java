import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class OrderData {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final String rentTime;
    public final String deliveryDate;
    public final String comment;

    public OrderData(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    @Step("Get new order data")
    public static OrderData getRandom() {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final String metroStation = "4";
        final String phone = "+7 800 355 35 35";
        final String rentTime = "5";
        final String deliveryDate = "2020-06-06";
        final String comment = RandomStringUtils.randomAlphabetic(10);
        return new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }
}
