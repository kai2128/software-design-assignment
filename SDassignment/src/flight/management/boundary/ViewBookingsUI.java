package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.entity.Bookings;
import flight.management.input.MenuInput;
import flight.management.lib.*;

import java.util.List;

public class ViewBookingsUI implements IBoundary {
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {

        displayTitle("View Bookings");
        IController controller =  ControllerFactory.getController("ViewBookings");
        controller.getData(LoginController.getLoggedInUserId());
    }

    public void displayBookingsList(List<Bookings> bookingsList) {
        DataPrinter.displayBookingsList(bookingsList);
    }

    public String promptAction() {
        int choice = -1;
        displayMsg("Do you want to:");
        displayOption("0. Back to main menu");
        displayOption("1. View specific bookings details (enter booking id)");

        choice = input.nextChoice(1);

        switch (choice){
            case 0 ->{
                UIFactory.getUI("ClientMenu").menu();
            }
            case 1 -> {
                promptInput("Enter booking id: ");
                return in.nextLine();
            }
        }
        return null;
    }

    public void displayBookingsDetails(Bookings bookings) {
        DataPrinter.displayBookingsDetails(bookings);
        displayMsg(String.format("Paid using bank card: XXXX-XXXX-XXXX-%s", bookings.getCardNumber().substring(15)));
    }
}
