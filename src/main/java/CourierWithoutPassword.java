import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutPassword {

    public final String login;
    public final String firstName;

    public CourierWithoutPassword(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    @Step("Get new courier without Password")
    public static CourierWithoutPassword withoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithoutPassword(login, firstName);
    }
}
