import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public final String login;
    public final String password;
    public final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Get new courier data")
    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier without login")
    public static Courier withoutLogin() {
        final String login = "";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier without password")
    public static Courier withoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = "";
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier without first name")
    public static Courier withoutFirstName() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = "";
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier without first name and login")
    public static Courier withoutFirstNameAndLogin() {
        final String login = "";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = "";
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier without first name and password")
    public static Courier withoutFirstNameAndPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = "";
        final String firstName = "";
        return new Courier(login, password, firstName);
    }

    @Step("Get new courier with wrong password")
    public static Courier withWrongPassword() {
        final String login = "Dfwe3433ff";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = "";
        return new Courier(login, password, firstName);
    }
}
