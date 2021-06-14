package flight.management.entity;

import flight.management.lib.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Flight {
    private final static int totalSeat = 40;

    private String flightCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String arrivalLocation ;
    private String departureLocation ;
    private double price;
    private ArrayList<FlightSeat> seatList = new ArrayList<FlightSeat>();
    private String flightStatus = "";
    private List<Bookings> bookingArr;


    public int getAvailableSeat(){
        return totalSeat - getSeatOccupied();
    }

    public int getSeatOccupied(){
        int counter = 0;

        for (FlightSeat seat : seatList) {
            //Add empty seat
            if(!seat.isAvailable()){
                counter++;
            }
        }

        return counter;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<FlightSeat> getSeatList() {
        return seatList;
    }

    public void setSeatList(ArrayList<FlightSeat> seatList) {
        this.seatList = seatList;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public List<Bookings> getBookingArr() {
        return bookingArr;
    }

    public void setBookingArr(List<Bookings> bookingArr) {
        this.bookingArr = bookingArr;
    }

    public String toSearchTable(){
        return String.format(("%s,%s,%s,%s,%s,%.2f,%s,%s"),
                flightCode,
                DateUtil.dateTimeToString(departureTime),
                DateUtil.dateTimeToString(arrivalTime),
                departureLocation,
                arrivalLocation,
                price,
                flightStatus,
                String.format("%d/%d", getSeatOccupied(),totalSeat)
                );
    }

    public Flight(Map<String, String> flightData) {
       this(
            flightData.get("code"),
            DateUtil.stringToDateTime(flightData.get("dep_time")),
            DateUtil.stringToDateTime(flightData.get("arr_time")),
            flightData.get("arr_loc"),
            flightData.get("dep_loc"),
            Double.parseDouble(flightData.get("price")),
            flightData.get("flight_status") == null ? "" : flightData.get("flight_status")
        );
    }

    public Flight(String flightCode, LocalDateTime departureTime, LocalDateTime arrivalTime, String arrivalLocation, String departureLocation, double price, String flightStatus) {
        this.flightCode = flightCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
        this.price = price;
        this.flightStatus = flightStatus;
    }
}
