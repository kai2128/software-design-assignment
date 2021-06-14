package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.MenuInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class CancelFlightUI implements IBoundary {
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        int choice = -1;
        displayTitle("Cancel FLight");

        if(LoginController.getSelectedFlight() == null){
            displayMsg("Flight not selected.");
            UIFactory.getUI("SearchFlight").menu();
        }


        LoginController.displaySelectedFlight();
        displayOption("0. Back");
        displayOption("1. Select another flight"); //to search
        displayOption("2. Cancel Selected Flight");

        choice = input.nextChoice(2);
        switch (choice){
            case 0 ->{
                UIFactory.getUI("ManageFlight").menu();
            }
            case 1 -> UIFactory.getUI("SearchFlight").menu();

            //pass flight code to be cancelled
            case 2 -> {
                displayWarning("Are you sure to cancel selected flight?");
                displayOption("1. Yes");
                displayOption("2. No");  // back to manage flight menu

                choice = input.nextChoice(1,2);

                if(choice == 2) UIFactory.getUI("ManageFlight").menu();

                //cancel selected flight
                ControllerFactory.getController("CancelFlight").getData(LoginController.getSelectedFlight().getFlightCode());
            }
        }
    }
}
