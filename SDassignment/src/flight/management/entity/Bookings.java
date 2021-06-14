package flight.management.entity;

import flight.management.dao.FlightDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bookings {
    private String bookingId;
    private String clientId;
    private List<Passenger> passengerArr;
    private Flight flight;
    private String cardNumber;
    private double totalPrice;
    private String bookingStatus;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Passenger> getPassengerArr() {
        return passengerArr;
    }

    public void setPassengerArr(List<Passenger> passengerArr) {
        this.passengerArr = passengerArr;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void calculateTotalPrice(){
        double total = 0;
        for (Passenger passenger : passengerArr) {
            total += passenger.getTicketPrice();
        }
        setTotalPrice(total);
    }

    public String[] getICList(){
        List<String> icList= new ArrayList<>();
        for (Passenger passenger : passengerArr) {
            icList.add(passenger.getIc());
        }
       return  icList.toArray(String[]::new);
    }

    public void makePayment(String cardNumber){
        this.cardNumber = cardNumber;
    }

    //for create booking in controller
    public Bookings(List<Passenger> passengerArr, Flight flight, String clientId) {
        this.passengerArr = passengerArr;
        this.flight = flight;
        this.clientId = clientId;
        calculateTotalPrice();
    }

    //for search result in database
    public Bookings(Map<String, String> result, List<Passenger> passengerArr){
        this.bookingId = result.get("id");
        this.flight = FlightDao.selectFlightUsingCode(result.get("flight_code"));
        this.clientId = result.get("c_id");
        this.cardNumber = result.get("card_number");
        this.totalPrice = Double.parseDouble(result.get("total_price"));
        this.bookingStatus = result.get("book_status");
        this.passengerArr = passengerArr;
    }

    public Bookings(String bookingId, String clientId,  Flight flight, List<Passenger> passengerArr, String cardNumber, double totalPrice, String bookingStatus) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.flight = flight;
        this.passengerArr = passengerArr;
        this.cardNumber = cardNumber;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    public String[] toTable() {
        return new String[]{bookingId, flight.getFlightCode(), String.valueOf(totalPrice), bookingStatus};
    }
}
