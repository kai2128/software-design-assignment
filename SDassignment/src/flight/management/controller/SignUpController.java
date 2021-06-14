package flight.management.controller;

import flight.management.dao.UserDao;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class SignUpController implements IController{
    private IBoundary ui = UIFactory.getUI("SignUp");

    @Override
    public void getData(String... signUpData) {
        if(UserDao.signUpUser(signUpData)){
            ui.displaySuccess("Sign Up Successes!");
            ControllerFactory.getController("Login").getData(signUpData[0],signUpData[4]);
        }else {
            ui.menu();
        }
    }
}
