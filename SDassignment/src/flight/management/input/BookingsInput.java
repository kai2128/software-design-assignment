package flight.management.input;

import flight.management.entity.Flight;
import flight.management.entity.FlightSeat;
import flight.management.lib.IBoundary;

import java.util.ArrayList;

public class BookingsInput extends BaseInput {
    public BookingsInput(IBoundary ui) {
        super(ui);
    }

    public static final String SEAT_CODE_RULE = "^[A-D][0-9]$";
    public static final String IC_RULE = "^\\d{6}-\\d{2}-\\d{4}$";


    public String nextPassengerNumber(Flight selectedFlight) {
        int seatAvailable = selectedFlight.getAvailableSeat();
        int number = -1;
        while (true) {
            ui.promptInput("Enter number of passenger (Enter 0 to cancel booking): ");
            number = in.nextInt();

            //loop
            if (number == -1){
                ui.displayError("Not a number!");
                continue;
            }

            if (number == 0 || number < seatAvailable)
                return String.valueOf(number);

            ui.displayError("Exceeded seat available");
        }
    }

    public String nextSeatCode(Flight selectedFlight, String[] selectedSeatCode) {
        ArrayList<FlightSeat> seatList = selectedFlight.getSeatList();
        String seatCode;
        while (true) {
            ui.promptInput("Enter seat code (0 to return) (eg: A0): ");
            seatCode = in.nextLine();
            boolean selectedCheck = true;

            //back to upper menu
            if (seatCode.equals("0"))
                return seatCode;

            if (seatCode.matches(SEAT_CODE_RULE)) {


                //check for selected seat only check for selected seat if it exist
                if(selectedSeatCode != null && selectedSeatCode.length>0){
                    for (String selectedSeat : selectedSeatCode) {
                        if (selectedSeat.equals(seatCode)){
                            ui.displayError("Flight seat already selected. Please select another one.");
                            selectedCheck = false;
                            break;
                        }else {
                            selectedCheck = true;
                        }
                    }
                }


                //if no repeated only check in database
                if(selectedCheck==true){
                    //check for booked flight
                    for (FlightSeat flightSeat : seatList) {
                        if (flightSeat.getSeatCode().equals(seatCode)) {
                            //check is selected flight seat is available
                            if (flightSeat.isAvailable())
                                return seatCode;
                            else{
                                ui.displayError("Flight seat already occupied. Please select another one.");
                            }
                        }
                    }
                }
            }else{
                ui.displayError("No such seat code. Please enter again.");
            }
        }
    }

    public String nextIC(String[] icSubmmited) {
        String ic;
        while (true) {
            ui.promptInput("Enter IC (123456-12-1234):" );
            ic = in.nextLine();
            boolean isICunique = true;

            if (ic.matches(IC_RULE)) {

                //check for selected seat only check for selected seat if it exist
                if(icSubmmited != null && icSubmmited.length>0){
                    for (String icExisted : icSubmmited) {
                        if (icExisted.equals(ic)){
                            ui.displayError("This ic has already submitted. Please enter a new one.");
                            isICunique = false;
                            break;
                        }
                    }
                }

                //if no repeated ic in the same order only can submit to boundary
                if(isICunique){
                    return ic;
                }
            }else{
                ui.displayError("Incorrect IC format. Please enter again.");
            }
        }
    }
}
