package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.MenuInput;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ManageBookingsUI implements IBoundary {
    private int choice = -1;
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        choice = -1;

        displayTitle("Manage Booking");

        LoginController.displaySelectedBookings();

        displayOption("0. Back");
        displayOption("1. Search Bookings");
        displayOption("2. View Bookings details");
        displayOption("3. Cancel Bookings");
        displayOption("4. Modify Bookings");

        choice = input.nextChoice( 4);



        switch (choice){
            case 0 ->{
                UIFactory.getUI("AdminMenu").menu();
            }
            case 1 -> UIFactory.getUI("SearchBookings").menu();
            case 2 -> UIFactory.getUI("ViewBookingsDetails").menu();
            case 3 -> UIFactory.getUI("CancelBookings").menu();
            case 4 -> UIFactory.getUI("ModifyBookings").menu();
        }
    }

}
