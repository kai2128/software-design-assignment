package flight.management.controller;

import flight.management.boundary.ViewBookingsUI;
import flight.management.dao.BookingsDao;
import flight.management.entity.Bookings;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

import java.util.List;

public class ViewBookingsController implements IController {

    private IBoundary ui = UIFactory.getUI("ViewBookings");


    @Override
    public void getData(String...cid) {
        String clientId = cid[0];

        List<Bookings> bookingsList = BookingsDao.getClientBookings(clientId);

        if(bookingsList != null){
            ((ViewBookingsUI)ui).displayBookingsList(bookingsList);
            String searchId = ((ViewBookingsUI)ui).promptAction();
            boolean flag = false;

            for (Bookings bookings : bookingsList) {
                if(bookings.getBookingId().equals(searchId)){
                    ((ViewBookingsUI)ui).displayBookingsDetails(bookings);
                    flag = true;
                }
            }
            if(flag == false){
                ui.displayError("No result found.");
            }

            ui.enterToCon();
            ui.menu();
        }else {
            backToMain();
        }
    }

    private void backToMain(){
        ui.displayMsg("No bookings found.");
        ui.enterToCon();

        //back to main menu
        UIFactory.getUI("ClientMenu").menu();
    }
}
