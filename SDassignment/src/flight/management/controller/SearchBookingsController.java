package flight.management.controller;

import flight.management.boundary.SearchBookingsUI;
import flight.management.dao.BookingsDao;
import flight.management.entity.Bookings;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

import java.util.Collections;
import java.util.List;

public class SearchBookingsController implements IController {

    private IBoundary ui = UIFactory.getUI("SearchBookings");

    @Override
    public void getData(String... searchFlightData) {

        //get user choice
        int choice = ((SearchBookingsUI) ui).promptAction();

        String id;
        if(choice == 1){
            //search
            //Display all orders
            ((SearchBookingsUI) ui).displayResult(BookingsDao.getAllBookings());
            id = ((SearchBookingsUI) ui).searchBookings();
            List<Bookings> bookingsList = BookingsDao.searchBookings(id);

            if(bookingsList.size()>0){
                ui.displaySuccess("Result found: ");

                //loop back to controller;
                ((SearchBookingsUI) ui).displayResult(bookingsList);
                getData();
            }

        }else {
            //select bookings
            id = ((SearchBookingsUI) ui).selectBookings();
            Bookings bookings = BookingsDao.selectBookings(id);

            if(bookings!=null){
                ((SearchBookingsUI) ui).displayResult(Collections.singletonList(bookings));
                LoginController.setSelectedBookings(bookings);
                ui.displaySuccess("Bookings selected");
                ui.enterToCon();

                UIFactory.getUI("AdminMenu").menu();
            }
        }

        //if no result
        ui.displayWarning("No result found");
        ui.enterToCon();
        ui.menu();

    }


}
