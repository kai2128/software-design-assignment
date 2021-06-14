package flight.management.controller;

import flight.management.dao.BookingsDao;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class CancelBookingsController implements IController {

    private IBoundary ui = UIFactory.getUI("CancelBookings");

    @Override
    public void getData(String... id) {
        if (BookingsDao.cancelBookings(id[0])) {
            //update
            LoginController.reSelectBookings(id[0]);
            //reprint selected flight details
            LoginController.displaySelectedBookingsDetails();
            ui.displaySuccess("Bookings Cancelled");
            //deselect flight
            LoginController.setSelectedBookings(null);
        } else {
            ui.displaySuccess("Failed to cancel bookings");
        }
        ui.enterToCon();
        //back to manage flight menu
        UIFactory.getUI("ManageBookings").menu();


    }
}
