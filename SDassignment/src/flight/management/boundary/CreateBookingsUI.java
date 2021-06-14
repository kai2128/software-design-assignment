package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.entity.Bookings;
import flight.management.entity.Passenger;
import flight.management.input.BookingsInput;
import flight.management.input.PaymentInput;
import flight.management.input.UserInput;
import flight.management.lib.*;

import java.util.ArrayList;
import java.util.List;

public class CreateBookingsUI implements IBoundary {
    private BookingsInput input = new BookingsInput(this);
    private UserInput uInput = new UserInput(this);
    private PaymentInput pInput = new PaymentInput(this);

    @Override
    public void menu() {
        int choice = -1;
        displayTitle("Create Bookings");

        if(LoginController.getSelectedFlight() == null){
            displayMsg("Flight not selected.");
            UIFactory.getUI("SearchFlight").menu();
        }

        LoginController.reSelectFlight();
        LoginController.displaySelectedFlight();
        displayOption("0. Back");
        displayOption("1. Select another flight"); //to search
        displayOption("2. Create bookings for this flight");

        choice = input.nextChoice(2);
        switch (choice){
            case 0 ->{
                UIFactory.getUI("ClientMenu").menu();
            }
            case 1 -> {
                UIFactory.getUI("SearchFlight").menu();
            }
            case 2 -> {
                createBookings();
            }
        }

    }

    private void createBookings() {
        String passengerNumber = input.nextPassengerNumber(LoginController.getSelectedFlight());

        if (passengerNumber.equals("0"))
            menu();
        blankLine();
        displayMsg("Available flight seat: ");
        line();
        DataPrinter.displayFlightSeat(LoginController.getSelectedFlight());
        line();
        IController controller = ControllerFactory.getController("CreateBookings");
        controller.getData(passengerNumber);
        //controller decide next step

    }

    public String[] getEachPassengerDetails(int i, String[] selectedSeatCode, String[] icSubmmited) {
        blankLine();
        displayMsg("Enter passenger " + i + " details");

        String seatCode = input.nextSeatCode(LoginController.getSelectedFlight(), selectedSeatCode);

        //return to create bookings menu
        if (seatCode.equals("0"))
            menu();

        String name = uInput.nextName();
        String ic = input.nextIC(icSubmmited);
        String email = uInput.nextEmail();
        String phoneNumber = uInput.nextPhone();

        //need to match with passenger entity
        return new String[]{name,ic,email,phoneNumber, seatCode, String.valueOf(LoginController.getSelectedFlight().getPrice())};
    }

    public void displayPassengerList(List<Passenger> passengerList){
        List<String[]> passengerData = new ArrayList<>();

        //convert to string array to display
        for (Passenger psg : passengerList) {
            passengerData.add(psg.toTable());
        }

        DataPrinter.displayPassengersList(passengerData);
    }

    public void displayBookingsDetails(Bookings bookings, List<Passenger> passengerList){
        displayMsg("Bookings details: ");
        line();
        LoginController.displaySelectedFlight();

        displayPassengerList(passengerList);

        displayMsg("Total price: RM " + bookings.getTotalPrice());
    }

    public String promptPayment() {
        String cardNumber = pInput.nextCardNumber();

        if(cardNumber.equals("0")){
            menu();
        }

        pInput.nextCardName();
        pInput.nextExpiryDate();
        pInput.nextCVV();
        pInput.nextTAC();

        return cardNumber;
    }
}
