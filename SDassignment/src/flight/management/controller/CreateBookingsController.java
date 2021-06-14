package flight.management.controller;

import flight.management.boundary.CreateBookingsUI;
import flight.management.dao.BookingsDao;
import flight.management.entity.Bookings;
import flight.management.entity.Passenger;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateBookingsController implements IController {
    private IBoundary ui = UIFactory.getUI("CreateBookings");

    @Override
    public void getData(String... data) {
        int passengerNumber = 0;


        passengerNumber = Integer.parseInt(data[0]);
        List<String[]> passengersData = new ArrayList<>();
        List<String> selectedSeatCode = new ArrayList<>();
        List<String> submittedIC = new ArrayList<>();

        //get all passenger details from ui
        for (int i = 0; i < passengerNumber; i++) {

            //if selected seat code exist already then only perform checking on duplication on ic and seat code
            if(selectedSeatCode.size()>0){
                passengersData.add(((CreateBookingsUI)ui).getEachPassengerDetails((i + 1),
                        selectedSeatCode.toArray(String[]::new),
                        submittedIC.toArray(String[]::new)));

            }else
                passengersData.add(((CreateBookingsUI)ui).getEachPassengerDetails((i + 1),
                        null,
                        null));


            //add selected seat code
            selectedSeatCode.add(passengersData.get(i)[5]);
            //record submitted ic
            submittedIC.add(passengersData.get(i)[1]);
        }


        //for create bookings
        ArrayList<Passenger> passengerList = new ArrayList<>();

        for (String[] passenger : passengersData) {
            passengerList.add(new Passenger(passenger));
        }

        Bookings newBookings = new Bookings(passengerList, LoginController.getSelectedFlight(),LoginController.getLoggedInUserId());

        ((CreateBookingsUI)ui).displayBookingsDetails(newBookings ,passengerList);
        String cardNumber = ((CreateBookingsUI)ui).promptPayment();
        ui.displayMsg("Contacting bank...");

        //record bank card number
        newBookings.makePayment(cardNumber);


        try {
            BookingsDao.createNewBookings(newBookings);
            ui.displaySuccess("Payment received. Bookings created.");
            ui.enterToCon();
        }catch (Exception e){
            //IC repeated
            ui.displayError("Failed to made payment. Bookings not created.");
            ui.enterToCon();
        }

            //back to client menu
        UIFactory.getUI("ClientMenu").menu();



//        Bookings tempBookings = new Bookings(passengerList, LoginController.getSelectedFlight(), );

    }

}
