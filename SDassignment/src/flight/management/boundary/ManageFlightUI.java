package flight.management.boundary;

import flight.management.input.MenuInput;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ManageFlightUI implements IBoundary {
    private int choice = -1;
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        choice = -1;

        displayTitle("Manage Flight");

        displayOption("0. Back");
        displayOption("1. Create flight");
        displayOption("2. View all flight");
        displayOption("3. Cancel flight");
        displayOption("4. Modify flight");
        displayOption("5. View passenger list");

        choice = input.nextChoice(5);
        switch (choice) {
            case 0 -> {
                UIFactory.getUI("AdminMenu").menu();
            }
            case 1 -> UIFactory.getUI("CreateFlight").menu();
            case 2 -> UIFactory.getUI("ViewAllFlight").menu();
            case 3 -> UIFactory.getUI("CancelFlight").menu();
            case 4 -> UIFactory.getUI("ModifyFlight").menu();
            case 5 -> UIFactory.getUI("ViewPassengerList").menu();
        }
    }
}
