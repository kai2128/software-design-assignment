package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.dao.FlightDao;
import flight.management.input.BookingsInput;
import flight.management.input.UserInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ModifyBookingsUI implements IBoundary {
    BookingsInput input = new BookingsInput(this);
    UserInput uInput = new UserInput(this);

    @Override
    public void menu() {
        int choice = -1;
        displayTitle("Modify Bookings");

        if(LoginController.getSelectedBookings() == null){
            displayMsg("Bookings not selected.");
            UIFactory.getUI("SearchBookings").menu();
        }

        //display bookings details
        LoginController.displaySelectedBookings();
        DataPrinter.displayFlight(LoginController.getSelectedBookings().getFlight());
        DataPrinter.displayPassengerListWithNumbering(LoginController.getSelectedBookings().getPassengerArr());
        displayMsg(String.format("Paid using bank card: XXXX-XXXX-XXXX-%s", LoginController.getSelectedBookings().getCardNumber().substring(15)));

        displayMsg("Select which passenger to be modified (enter 0 to back): ");
        promptInput("Enter passenger no. to select, ");

        choice = input.nextChoice(LoginController.getSelectedBookings().getPassengerArr().size());

        if(choice ==0 ){
            UIFactory.getUI("ManageBookings").menu();
        }

        //pass passenger number to controller
        ControllerFactory.getController("ModifyBookings").getData(String.valueOf(choice));
    }

    public void displayModifyOptions(String ic){
        displayMsg("Select which details to modify (Once items is selected cannot enter 0 to back)");
        displayOption("0. Back");
        displayOption("1. Name");
        displayOption("2. IC");
        displayOption("3. Email");
        displayOption("4. Phone");
        displayOption("5. Selected Seat");


        int choice = input.nextChoice(5);
        String newData = null;
        switch (choice){
            case 0 -> menu();
            case 1 -> newData = uInput.nextName();
            case 2 -> newData = input.nextIC(LoginController.getSelectedBookings().getICList());
            case 3 -> newData = uInput.nextEmail();
            case 4 -> newData = uInput.nextPhone();
            case 5 -> {
                displayMsg("Available flight seat: ");
                line();
                DataPrinter.displayFlightSeat(FlightDao.selectFlightUsingCode(LoginController.getSelectedBookings().getFlight().getFlightCode()));
                line();
                newData = input.nextSeatCode(LoginController.getSelectedBookings().getFlight(), null);
            }
        }

        ControllerFactory.getController("ModifyBookings").getData(String.valueOf(choice), ic, newData);
    }
}
