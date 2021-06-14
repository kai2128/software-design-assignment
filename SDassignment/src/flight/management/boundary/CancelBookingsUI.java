package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.MenuInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class CancelBookingsUI implements IBoundary {
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        int choice = -1;
        displayTitle("Cancel Booking");

        //go to search bookings if no bookings selected
        if(LoginController.getSelectedBookings() == null){
            displayMsg("Bookings not selected.");
            UIFactory.getUI("SearchBookings").menu();
        }

        LoginController.displaySelectedBookingsDetails();
        displayOption("0. Back");
        displayOption("1. Cancel Selected Bookings");

        choice = input.nextChoice(1);

        //return to manage booking menu
        if(choice == 0){
            UIFactory.getUI("ManageBookings").menu();
        }

        displayWarning("Are you sure to cancel selected bookings? ");
        displayOption("1. Yes");
        displayOption("2. No "); // back

        choice = input.nextChoice(1, 2);

        if(choice == 2){
            UIFactory.getUI("ManageBookings").menu();
        }

        //cancel bookings
        ControllerFactory.getController("CancelBookings").getData(LoginController.getSelectedBookings().getBookingId());



    }
}
