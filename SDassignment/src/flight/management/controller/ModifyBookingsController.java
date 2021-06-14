package flight.management.controller;

import flight.management.boundary.ModifyBookingsUI;
import flight.management.dao.BookingsDao;
import flight.management.entity.Passenger;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

import java.util.List;

public class ModifyBookingsController implements IController {
    IBoundary ui = UIFactory.getUI("ModifyBookings");

    @Override
    public void getData(String... data) {
        String ic ="";
        List<Passenger> passengerArr = LoginController.getSelectedBookings().getPassengerArr();
        //received number selection from ui
        if(data.length==1){
            int selectedNumber = Integer.parseInt(data[0]);

            //minus 1 to select from arraylist
            selectedNumber--;
            ic = passengerArr.get(selectedNumber).getIc();
            //pass ic to boundary
            ((ModifyBookingsUI)ui).displayModifyOptions(ic);
        }

        //perform modification
        String choice = data[0];
        ic= data[1];
        String bookId = LoginController.getSelectedBookings().getBookingId();
        boolean isModified = false;

        switch (choice){
            case "1" -> isModified = BookingsDao.modifyPassenger(bookId, ic, data[2],"p_name");
            case "2" -> isModified = BookingsDao.modifyPassenger(bookId, ic, data[2],"p_ic");
            case "3" -> isModified = BookingsDao.modifyPassenger(bookId, ic, data[2],"p_email");
            case "4" -> isModified = BookingsDao.modifyPassenger(bookId, ic, data[2],"p_phone");
            case "5" -> isModified = BookingsDao.modifyPassenger(bookId, ic, data[2],"selected_seat");
        }

        if(isModified){
            ui.displaySuccess("Details updated.");
            LoginController.reSelectBookings();
            ui.displayMsg("Bookings details: ");
            LoginController.displaySelectedBookings();
            DataPrinter.displayBookingsDetails(LoginController.getSelectedBookings());
            ui.enterToCon();
            ui.menu(); // back to modify menu
        } else {
            ui.displayError("Not updated.");
            ui.enterToCon();
        }


        //Back to admin menu
        UIFactory.getUI("AdminMenu").menu();


    }
}
