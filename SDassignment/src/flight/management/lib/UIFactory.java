package flight.management.lib;

import flight.management.boundary.*;

import java.util.HashMap;
import java.util.Map;

public class UIFactory {

    private static final Map<String, IBoundary> UIMap = new HashMap<>();

    public static IBoundary getUI(String className) {
        IBoundary ui = null;

        if(UIMap.containsKey(className)){
            ui = UIMap.get(className);
        } else {

            switch (className){
                //User
                case "Launch" -> ui = new LaunchUI();
                case "SignUp" -> ui = new SignUpUI();
                case "Login" -> ui = new LoginUI();

                //client
                case "ClientMenu" -> ui = new ClientMenuUI();
                case "SearchFlight" -> ui = new SearchFlightUI();
                case "CreateBookings" -> ui = new CreateBookingsUI();
                case "ViewBookings" -> ui = new ViewBookingsUI();

                //admin
                case "AdminMenu" -> ui = new AdminMenuUI();


                //Manage Flight
                case "ManageFlight" -> ui = new ManageFlightUI();
                case "CreateFlight" -> ui = new CreateFlightUI();
                case "ViewAllFlight" -> ui = new ViewAllFlightUI();
                case "CancelFlight" -> ui = new CancelFlightUI();
                case "ModifyFlight" -> ui = new ModifyFlightUI();
                case "ViewPassengerList" -> ui = new ViewPassengerListUI();

                //Manage Bookings
                case "ManageBookings" -> ui = new ManageBookingsUI();
                case "SearchBookings" -> ui = new SearchBookingsUI();
                case "ViewBookingsDetails" -> ui = new ViewBookingsDetailsUI();
                case "ModifyBookings" -> ui = new ModifyBookingsUI();
                case "CancelBookings" -> ui = new CancelBookingsUI();




            }
            UIMap.put(className, ui);
        }
        return ui;
    }

}
