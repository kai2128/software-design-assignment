package flight.management.boundary;

import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class LoginUI implements IBoundary {

    //input from user
    private String username;
    private String password;

    @Override
    public void menu() {
        displayTitle("Login");
        promptInput("Enter username (0 to back): ");
        username= in.nextLine();

        //if user enter 0 then back to launch menu
        if(username.equals("0")){
            UIFactory.getUI("Launch").menu();
        }

        promptInput("Enter password: ");
        password= in.nextLine();

        //pass login data to controller
        IController controller = ControllerFactory.getController("Login");
        controller.getData(username, password);
    }


}
