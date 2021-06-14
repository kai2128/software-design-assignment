package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.MenuInput;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;


public class AdminMenuUI implements IBoundary {
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        int choice = -1;

        displayTitle("Admin Menu");


        displayMsg("Selected Flight: " + (LoginController.getSelectedFlight()!=null?LoginController.getSelectedFlight().getFlightCode():"NONE"));
        displayMsg("Selected Bookings: " + (LoginController.getSelectedBookings()!=null?LoginController.getSelectedBookings().getBookingId():"NONE"));

//        System.out.println("Welcome, " + LoginController.getLoggedInUser().toString());
        displayOption("1. Search flight");
        displayOption("2. Manage flight");
        displayOption("3. Manage Booking");
        displayOption("4. Logout");

        choice = input.nextChoice( 4);
        switch (choice){
            case 0,4 ->{
                LoginController.logout();
                UIFactory.getUI("Login").menu();
            }
            case 1 -> UIFactory.getUI("SearchFlight").menu();
            case 2 -> UIFactory.getUI("ManageFlight").menu();
            case 3 -> UIFactory.getUI("ManageBookings").menu();
        }


    }

}
