package flight.management.controller;

import flight.management.dao.BookingsDao;
import flight.management.dao.FlightDao;
import flight.management.dao.UserDao;
import flight.management.entity.*;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

import java.util.ArrayList;
import java.util.List;

public class LoginController implements IController {
    private String name;
    private String password;

    //session controll
    private static User loggedInUser = null;
    private static Flight selectedFlight = null;
    private static Bookings selectedBookings = null;

    public static void setSelectedFlight(Flight flight){
        selectedFlight = flight;
    }

    public static Flight getSelectedFlight(){
        return selectedFlight;
    }

    //use to update selected flight details
    public static void reSelectFlight(String flightCode){
        setSelectedFlight(FlightDao.selectFlightUsingCode(flightCode));
    }
    public static void reSelectFlight(){
        setSelectedFlight(FlightDao.selectFlightUsingCode(LoginController.getSelectedFlight().getFlightCode()));
    }

    //use to update selected bookings
    public static void reSelectBookings(String id) {
        setSelectedBookings(BookingsDao.selectBookings(id));
    }
    public static void reSelectBookings() {
        setSelectedBookings(BookingsDao.selectBookings(selectedBookings.getBookingId()));
    }



    public static void displaySelectedFlight(){
        IBoundary displayUI = UIFactory.getUI("SearchFlight");

        if(selectedFlight != null){
            displayUI.displayMsg("Selected flight: ");
            DataPrinter.displayFlight(selectedFlight);
        } else {
            displayUI.displayMsg("No flight selected.");
        }
    }

    public static void displaySelectedBookings(){
        IBoundary displayUI = UIFactory.getUI("ManageBookings");

        if(selectedBookings != null){
            displayUI.displayMsg("Selected bookings: ");
            DataPrinter.displayBookings(selectedBookings);
        } else {
            displayUI.displayMsg("No bookings selected.");
        }
    }

    public static void displaySelectedBookingsPsgList(){
        List<String[]> passengerData = new ArrayList<>();

        //convert to string array to display
        for (Passenger psg : getSelectedBookings().getPassengerArr()) {
            passengerData.add(psg.toTable());
        }

        DataPrinter.displayPassengersList(passengerData);
    }

    public static void displaySelectedBookingsDetails(){

        IBoundary displayUI = UIFactory.getUI("ManageBookings");

        if(selectedBookings != null){
            displayUI.displayMsg("Selected bookings details: ");
            displayUI.line();
            DataPrinter.displayBookings(selectedBookings);
            //display bookings flight
            DataPrinter.displayFlight(FlightDao.selectFlightUsingCode(selectedBookings.getFlight().getFlightCode()));
            displaySelectedBookingsPsgList();
            displayUI.displayMsg(String.format("Paid using bank card: XXXX-XXXX-XXXX-%s", getSelectedBookings().getCardNumber().substring(15)));

        } else {
            displayUI.displayMsg("No bookings selected.");
        }
    }

    public static void setSelectedBookings(Bookings booking) {
        selectedBookings = booking;
    }

    public static Bookings getSelectedBookings() {
        return selectedBookings;
    }

    public static void logout(){
        selectedBookings = null;
        selectedFlight = null;
        loggedInUser = null;
    }

    //used to retrieve logged user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static String getLoggedInUserId() {
        //remove first letter from user id

        if(isClient()){
            return ((Client)loggedInUser).getClientId().substring(1);
        }else {
            return ((Admin)loggedInUser).getAdminId().substring(1);
        }
    }


    public static boolean isClient() {
        return loggedInUser instanceof Client;
    }

    //session controll



    private IBoundary ui = UIFactory.getUI("Login");



    public static void  displayLoggedInMenu(){
        if(loggedInUser instanceof Admin){
            UIFactory.getUI("AdminMenu").menu();
        }else {
            UIFactory.getUI("ClientMenu").menu();
        }
    }

    /**
     * Get username and password input from UI
     * @param loginData
     */
    @Override
    public void getData(String... loginData) {
        this.name=loginData[0];
        this.password=loginData[1];
        if(validateLogin()){
            displayLoggedInMenu();
        }else{
           ui.displayError("Username and password does not match.");
           ui.line();
           ui.menu();
        }

    }

    private boolean validateLogin() {
        boolean logged = false;


        loggedInUser = UserDao.login(name, password);

        if(loggedInUser != null) logged=true;

        return logged;
    }
}
