package flight.management.boundary;

import flight.management.entity.Bookings;
import flight.management.input.MenuInput;
import flight.management.lib.*;

import java.util.List;

public class SearchBookingsUI implements IBoundary{
    //A boundary basic fields
    private MenuInput input = new MenuInput(this);
    private int choice;


    @Override
    public void menu() {
        choice = -1;

        displayTitle("Search Bookings");
        IController control = ControllerFactory.getController("SearchBookings");
        control.getData();
    }

    public SearchBookingsUI displayResult(List<Bookings> bookings){
        DataPrinter.displayBookingsList(bookings);
        return this;
    }

    public int promptAction(){

        displayOption("0. Back");
        displayOption("1. Search Bookings");
        displayOption("2. Select Bookings ");

        choice = input.nextChoice(2);

        if(choice==0){
            UIFactory.getUI("ManageBookings").menu();
        }

        return choice;
    }

    public String searchBookings(){
        promptInput("Enter bookings id to search: ");
        return in.nextLine();
    }

    public String selectBookings() {
        promptInput("Enter bookings id to select: ");

        return in.nextLine();
    }

}
