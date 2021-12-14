import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutFirstNameAndPassword {

    public final String login;

    public CourierWithoutFirstNameAndPassword(String login) {
        this.login = login;
    }

    @Step("Get new courier data without first name and password")
    public static CourierWithoutFirstNameAndPassword withoutFirstNameAndPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithoutFirstNameAndPassword(login);
    }
}
