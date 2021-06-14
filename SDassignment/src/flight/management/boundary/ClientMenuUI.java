package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.MenuInput;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class ClientMenuUI implements IBoundary {
    private int choice;
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        choice = -1;
        System.out.println();
        displayTitle("Client Menu");
        displayMsg("Welcome, " + LoginController.getLoggedInUser().getName());
        displayOption("1. Search for flight");
        displayOption("2. Create bookings");
        displayOption("3. View bookings");
        displayOption("4. Logout");

        choice = input.nextChoice(4);

        switch (choice){
            case 0,4 ->{
                LoginController.logout();
                UIFactory.getUI("Login").menu();
            }
            case 1 -> UIFactory.getUI("SearchFlight").menu();
            case 2 -> UIFactory.getUI("CreateBookings").menu();
            case 3 -> UIFactory.getUI("ViewBookings").menu();
        }
    }

}
