package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ViewBookingsDetailsUI implements IBoundary {

    @Override
    public void menu() {

        displayTitle("View Bookings Details");

        if(LoginController.getSelectedBookings() == null){
            displayMsg("No bookings selected.");
            UIFactory.getUI("SearchBookings").menu();
        }

        DataPrinter.displayBookingsDetails(LoginController.getSelectedBookings());
        displayMsg(String.format("Paid using bank card: XXXX-XXXX-XXXX-%s", LoginController.getSelectedBookings().getCardNumber().substring(15)));
        enterToCon();

        UIFactory.getUI("ManageBookings").menu();
    }

}
