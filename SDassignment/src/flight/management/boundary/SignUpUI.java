package flight.management.boundary;

import flight.management.input.UserInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class SignUpUI implements IBoundary {
    private UserInput input = new UserInput(this);

    private String username;
    private String name;
    private String phone;
    private String email;
    private String password;

    @Override
    public void menu() {
        IController controller = null;

        displayTitle("Sign Up");
        username = input.nextUsername();
        if(username.equals("0")){
            UIFactory.getUI("Launch").menu();
        }

        name = input.nextName();
        phone = input.nextPhone();
        email = input.nextEmail();
        password = input.nextPassword();

        controller = ControllerFactory.getController("SignUp");
        controller.getData(username, name, phone, email, password);
    }
}
