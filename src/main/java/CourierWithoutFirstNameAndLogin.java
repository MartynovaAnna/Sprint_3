import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierWithoutFirstNameAndLogin {

        public final String password;

        public CourierWithoutFirstNameAndLogin(String password) {
            this.password = password;
        }

        @Step("Get new courier data without first name and login")
        public static CourierWithoutFirstNameAndLogin withoutFirstNameAndLogin() {
            final String password = RandomStringUtils.randomAlphabetic(10);
            return new CourierWithoutFirstNameAndLogin(password);
        }
}
