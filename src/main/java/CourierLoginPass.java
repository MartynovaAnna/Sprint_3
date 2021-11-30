public class CourierLoginPass {

    public final String login;
    public final String password;

    public CourierLoginPass(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierLoginPass from(Courier courier) {
        return new CourierLoginPass(courier.login, courier.password);
    }

    public static CourierLoginPass from(CourierWithWrongPassword courierWithWrongPassword) {
        return new CourierLoginPass(courierWithWrongPassword.login, courierWithWrongPassword.password);
    }
}
