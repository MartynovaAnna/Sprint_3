import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutLogin {

    public final String password;
    public final String firstName;

    public CourierWithoutLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Get new courier without Login")
    public static CourierWithoutLogin withoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierWithoutLogin(password, firstName);
    }
}
