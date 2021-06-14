package flight.management.lib;

import flight.management.entity.Bookings;
import flight.management.entity.Flight;
import flight.management.entity.FlightSeat;
import flight.management.entity.Passenger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataPrinter {

    //for single bookings
    public static void displayBookings(Bookings bookings){
        displayBookingsList(List.of(bookings));
    }




    public static void displayBookingsList(List<Bookings> bookings){
        String[] header = {"Booking id", "Flight Code", "Total Price", "Booking Status"};

        List<String[]> data = new ArrayList<>();


        for (Bookings booking : bookings) {
            data.add(booking.toTable());
        }

        TablePrinter.printTableWithColumn(data, header);

    }

    public static void displayBookingsDetails(Bookings booking){
        displayFlight(booking.getFlight());
        convertToDisplayPassenger(booking.getPassengerArr());
    }

    public static void displayFlightSearchResults(List<Flight> flights){
        String[] header = {"Code", "Dep Time", "Arr Time", "Dep Loc", "Arr Loc", "Price", "Status", "Seat"};

        List<String> data = new ArrayList<>();


        for (Flight flight : flights) {
            data.add(flight.toSearchTable());
        }

        TablePrinter.printTable(data,header);
    }

    public static void displayFlight(Flight flight){
        displayFlightSearchResults(List.of(flight));
    }

    public static void displayFlightSeat(Flight selectedFlight) {
//          A  B    C  D
//        0[ ][ ]  [ ][ ]
//        1[ ][ ]  [ ][ ]
//        2[ ][X]  [ ][ ]
//        3[ ][ ]  [ ][ ]
//        4[ ][X]  [ ][ ]
//        5[ ][ ]  [ ][ ]
//        6[ ][ ]  [ ][ ]
//        7[ ][ ]  [ ][ ]
//        8[ ][ ]  [ ][X]
//        9[ ][ ]  [ ][ ]

        List<String> table = new ArrayList<>();

        table.add("      A  B    C  D");

        List<String> fourCell = new ArrayList<>();
        int counter = 0;
        int numbering = 0;

        List<FlightSeat> numberSortedSeat = selectedFlight.getSeatList()
                        .stream()
                        .sorted(Comparator.comparing(s -> s.getSeatCode().substring(1))).collect(Collectors.toList());

        for (FlightSeat flightSeat : numberSortedSeat) {
            counter++;
            fourCell.add(flightSeat.isAvailable()?" ":"X");

            if(counter == 4){

                String singleRow = String.format("    %d[%s][%s]  [%s][%s]",
                        numbering, //number
                        fourCell.get(0),
                        fourCell.get(1),
                        fourCell.get(2),
                        fourCell.get(3)
                );
                table.add(singleRow);
                //reset cell
                fourCell.clear();
                //reset counter
                counter = 0;
                //add numbering
                numbering++;
            }
        }

        //print table
        table.forEach(System.out::println);
    }

//    public static void displayFlightPassengerList(Flight passengerList){
//        String[] header = {"No.","Name", "IC", "Passport", "Email", "Phone", "Selected Seat", "Ticket Price"};
//
//        List<String> passengerData = new ArrayList<>();
//        int numbering = 1;
//
//        for (int i =0; i < passengerList.size(); i++) {
//            passengerData.add(String.format("%d,%s",
//                    numbering,
//                    passengerList.get(i).toNumberedTable()));
//
//            numbering++;
//        }
//
//    }

    public static void displayFlightPassengerList(Flight flight){

        List<String> data = new ArrayList<>();
        List<Bookings> bookings = flight.getBookingArr();

        String[] header = {"No.", "Book id","Name", "IC", "Email", "Phone", "Selected Seat", "Ticket Price"};

        int numbering = 1;

        for (Bookings booking : bookings) {

            for (Passenger passenger : booking.getPassengerArr()) {
                data.add(String.format("%d,%s,", numbering, booking.getBookingId()) + String.format("%s", passenger.toNumberedTable()));
                numbering++;
            }
        }

        TablePrinter.printTable(data, header);
    }

    public static void displayPassengerListWithNumbering(List<Passenger> passengerList){
        String[] header = {"No.","Name", "IC", "Email", "Phone", "Selected Seat", "Ticket Price"};

        List<String> passengerData = new ArrayList<>();
        int numbering = 1;

        for (int i =0; i < passengerList.size(); i++) {
            passengerData.add(String.format("%d,%s",
                    numbering,
                    passengerList.get(i).toNumberedTable()));

            numbering++;
        }

        TablePrinter.printTable(passengerData, header);
    }

    //
    public static void convertToDisplayPassenger(List<Passenger> passenger){

        ArrayList<String[]> passengerData = new ArrayList<>();
        for (Passenger psg : passenger) {
            passengerData.add(psg.toTable());
        }

        displayPassengersList(passengerData);

    }

    public static void displayPassengersList(List<String[]> passenger){

        String[] header = {"Name", "IC", "Email", "Phone", "Selected Seat", "Ticket Price"};

        TablePrinter.printTableWithColumn(passenger, header);

    }
}
