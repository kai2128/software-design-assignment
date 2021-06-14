package flight.management.lib;


import flight.management.controller.*;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {


    private static final Map<String, IController> controllerMap = new HashMap<>();

    public static IController getController(String className) {
        IController ctrl = null;

        if(controllerMap.containsKey(className)){
            ctrl = controllerMap.get(className);
        } else {

            switch (className){
                case "Login" -> ctrl = new LoginController();
                case "SignUp" -> ctrl = new SignUpController();
                case "SearchFlight" -> ctrl = new SearchFlightController();
                case "SelectFlight" -> ctrl = new SelectFlightController();
                case "CreateBookings" -> ctrl = new CreateBookingsController();
                case "ViewBookings" -> ctrl = new ViewBookingsController();

                //Admin
                case "CancelBookings" -> ctrl = new CancelBookingsController();
                case "ModifyBookings" -> ctrl = new ModifyBookingsController();

                //Manage Flight
                case "CancelFlight" -> ctrl = new CancelFlightController();
                case "CreateFlight" -> ctrl = new CreateFlightController();
                case "ModifyFlight" -> ctrl = new ModifyFlightController();

                //Manage Bookings
                case "SearchBookings" -> ctrl = new SearchBookingsController();

            }
            controllerMap.put(className, ctrl);
        }
        return ctrl;
    }


}
