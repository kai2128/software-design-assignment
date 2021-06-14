package flight.management.boundary;

import flight.management.controller.LoginController;
import flight.management.input.FlightInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;

public class
SearchFlightUI implements IBoundary{
    //A boundary basic fields
    private FlightInput input = new FlightInput(this);
    private IController control;
    private int choice;

    //for client & admin search fields
    private String arrLoc;
    private String depLoc;
    private String date;

    //for admin search
    private String flightCode;


    @Override
    public void menu() {
        choice = -1;

        displayTitle("Search Flight");
        if (LoginController.isClient()){
            searchUsingLocAndDate();
        }else {
            displayAdminOption();
        }
    }

    private void searchUsingLocAndDate() {
        arrLoc = input.nextArrLocation();

        //Back to client / admin menu if arrLoc is 0
        if(arrLoc.equals("0")){
            LoginController.displayLoggedInMenu();
        }

        depLoc = input.nextDepLocation();
        date = input.nextDate();

        control = ControllerFactory.getController("SearchFlight");
        control.getData(arrLoc, depLoc, date);
        //controller will decide whether to display data or input again.
    }

    //OR

    private void searchUsingCode() {
        promptInput("Enter flight code: ");
        flightCode = in.nextLine();
        if (flightCode.equals("0")){
            menu(); //return to admin option if next input is 0
        }

        control = ControllerFactory.getController("SearchFlight");
        control.getData(flightCode);
        //controller will decide whether to display data or input again.
    }

    private void displayAdminOption() {
        displayOption("0. Back");
        displayOption("1. Select using flight code");
        displayOption("2. Search using departure location, arrival location and date");
        choice = input.nextChoice(2);

        switch (choice){
            case 1-> searchUsingCode();
            case 2-> searchUsingLocAndDate();
            case 0-> LoginController.displayLoggedInMenu();
        }
    }

    //if result was displayed
    public void displayActionOption(){
        System.out.println("Do you want to:");
        displayOption("0. Back");
        displayOption("1. Search again");
        displayOption("2. Select flight");
        choice = input.nextChoice(2);

        switch (choice){
            case 1-> menu();
            case 2-> selectFlight();
            case 0-> {
               LoginController.displayLoggedInMenu();
            }
        }


    }

    private void selectFlight() {
        promptInput("Enter flight code: ");
        flightCode = in.nextLine();

        control = ControllerFactory.getController("SelectFlight");
        control.getData(flightCode);
    }
}
