package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.dao.BookingsDao;
import flight.management.entity.Flight;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ViewPassengerListUI implements IBoundary{

    @Override
    public void menu() {

        displayTitle("View Passenger List");

        if(LoginController.getSelectedFlight() == null){
            displayMsg("Flight not selected.");
            UIFactory.getUI("SearchFlight").menu();
        }

        //print flight details
        LoginController.displaySelectedFlight();

        Flight flightDetails = LoginController.getSelectedFlight();

        //add bookings into flight
        flightDetails.setBookingArr(BookingsDao.selectBookingsUsingFlightCode(LoginController.getSelectedFlight().getFlightCode()));



        //add order id
        if(flightDetails.getBookingArr().size()>0){
            displayMsg("List of passenger");
            line();
            DataPrinter.displayFlightPassengerList(flightDetails);
            enterToCon();
        }else {
            displayMsg("No passenger currently.");
            enterToCon();
        }

        UIFactory.getUI("AdminMenu").menu();
    }

}
