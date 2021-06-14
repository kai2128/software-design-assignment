package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.FlightInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ModifyFlightUI implements IBoundary {
    FlightInput input = new FlightInput(this);

    private String dep_time;
    private String arr_time;
    private String dep_loc;
    private String arr_loc;
    private double price;
    private String status;

    @Override
    public void menu() {
        int choice = -1;
        displayTitle("Modify FLight");

        if(LoginController.getSelectedFlight() == null){
            displayMsg("Flight not selected.");
            UIFactory.getUI("SearchFlight").menu();
        }


        LoginController.displaySelectedFlight();
        displayOption("0. Back");
        displayOption("1. Select another flight"); //to search
        displayOption("2. Modify Selected Flight");

        choice = input.nextChoice(2);
        switch (choice){
            case 0 ->{
                UIFactory.getUI("ManageFlight").menu();
            }
            case 1 -> UIFactory.getUI("SearchFlight").menu();

            //pass flight code to be cancelled
            case 2 -> displayModifyOptions();
        }

    }

    private void displayModifyOptions() {
        int choice = -1;
        displayMsg("Select which details to modify (Once items is selected cannot enter 0 to back): ");
        displayOption("1. Flight Code");
        displayOption("2. Departure Time");
        displayOption("3. Arrival Time");
        displayOption("4. Departure Location");
        displayOption("5. Arrival Location");
        displayOption("6. Price");
        displayOption("7. Status");
        choice = input.nextChoice(7);
        String newData = null;

        switch (choice){
            case 0 -> {
                UIFactory.getUI("ManageFlight").menu();
            }
            case 1 -> newData = input.modifyFlightCode();
            case 2 -> newData = input.modifyDepTime(LoginController.getSelectedFlight());
            case 3 -> newData = input.modifyArrTime(LoginController.getSelectedFlight());
            case 4 -> newData = input.nextArrLocation();
            case 5 -> newData = input.nextDepLocation();
            case 6 -> newData = String.valueOf(input.nextPrice());
            case 7 -> {
                promptInput("Enter flight status: ");
                newData = in.nextLine();
            }
        }
        ControllerFactory.getController("ModifyFlight").getData(String.valueOf(choice), newData);
    }
}
