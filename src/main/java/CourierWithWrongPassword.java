import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithWrongPassword {

    public final String login;
    public final String password;

    public CourierWithWrongPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Get new courier data with wrong password")
    public static CourierWithWrongPassword withWrongPassword() {
        final String login = "Dfwe3433ff";
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithWrongPassword(login, password);
    }
}
