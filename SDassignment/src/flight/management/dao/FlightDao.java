package flight.management.dao;

import flight.management.entity.Flight;
import flight.management.entity.FlightSeat;
import flight.management.entity.Passenger;
import flight.management.lib.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightDao extends BaseDao {

    public static List<Passenger> getAllPassengerList(String flightCode){
//        SELECT * FROM passenger WHERE book_id IN
//        (SELECT id FROM bookings WHERE flight_code = 'TE01')
        String sql = String.format("SELECT * FROM passenger WHERE book_id IN (SELECT id FROM bookings WHERE flight_code = '%s')",
                flightCode);

        return BookingsDao.convertPassenger(db.selectAll(sql));
    }

    public static List<Flight> getAllFlight(){
        List<Map<String, String>> result = db.selectAll("SELECT * FROM flight");

        return convertToFlightList(result);
    }

    public static List<Flight> searchFlightUsingLoc (String...searchInput){
        String arrLoc = searchInput[0];
        String depLoc = searchInput[1];
        String date = searchInput[2];

        //get a date range of 20 days
        LocalDate startDate = DateUtil.stringToDate(date);
        startDate = startDate.minusDays(10);
        LocalDate endDate = DateUtil.stringToDate(date);
        endDate = endDate.plusDays(10);

        //sample: SELECT * FROM flight WHERE arr_loc LIKE '%a%' AND dep_loc LIKE '%a%' AND dep_time BETWEEN '2021-03-11' AND '2021-03-20'
        //match: 2021-03-11 16:02:45 test test
        String sql = String.format("SELECT * FROM flight WHERE arr_loc LIKE '%%%s%%' AND dep_loc LIKE '%%%s%%' AND dep_time BETWEEN '%s' AND '%s'",
                arrLoc, depLoc, DateUtil.dateToString(startDate), DateUtil.dateToString(endDate));

        List<Map<String, String>> result = db.selectAll(sql);

        return convertToFlightList(result);
    }

    private static List<Flight> convertToFlightList (List<Map<String, String>> searchResult) {
        List<Flight> flightResult = new ArrayList<>();

        if (searchResult.size() > 0) {
            for (Map<String, String> row : searchResult) {

                //add flight object using constructor
                flightResult.add(new Flight(row));

                //add seat list to flight
                flightResult.get(flightResult.size() - 1 ).setSeatList((ArrayList<FlightSeat>) addFlightSeat(row.get("code")));
            }
        }
        return flightResult;
    }

    private static List<FlightSeat> addFlightSeat(String flightCode){
        //SELECT seat_code,is_avail FROM `flight_seat` WHERE flight_code='TE01'
        String seatSql = "SELECT seat_code,is_avail FROM flight_seat WHERE flight_code='" + flightCode + "'";

        List<Map<String, String>> seatDataList = db.selectAll(seatSql);
        List<FlightSeat> seatList = new ArrayList<>();

        for (Map<String, String> seat : seatDataList) {
            //add seat to each flight
           seatList.add(new FlightSeat(seat));
        }
        return seatList;
    }


    public static List<Flight> searchFlightUsingCode (String flightCode) {
        List<Flight> result = new ArrayList<>();

        String sql = String.format("SELECT * FROM flight WHERE code='%%%s%%'", flightCode);
        List<Map<String, String>> searchResult = db.selectAll(sql);

        result = convertToFlightList(searchResult);

        return result;
    }

    public static Flight selectFlightUsingCode(String flightCode) {
        Flight result = null;


        String sql = String.format("SELECT * FROM flight WHERE code='%s'", flightCode);

        if(db.rowCount(sql) < 1){
            return null;
        }
        Map<String, String> searchResult = db.selectOne(sql);

        result = new Flight(searchResult);

        result.setSeatList((ArrayList<FlightSeat>) addFlightSeat(result.getFlightCode()));

        return result;
    }

    public static void cancelFlight(String code) {

//        if flight already cancel
        if (selectFlightUsingCode(code).getFlightStatus().equals("CANCELLED")){
            return;
        }

//        UPDATE flight SET flight_status='Cancelled' WHERE code='TE01'
        String sql = String.format("UPDATE flight SET flight_status='CANCELLED' WHERE code='%s'", code);
        if(db.execUpdate(sql)>0){

            //set bookings status to flight cancelled
//            UPDATE bookings SET `book_status`=CONCAT(book_status, ', FLIGHT CANCELLED') WHERE flight_code=TE01
            String bookingsSql = String.format("UPDATE bookings SET `book_status`=CONCAT('FLIGHT CANCELLED, ', book_status) WHERE flight_code='%s'",code);
            db.execUpdate(bookingsSql);
        }

    }

    public static boolean insertNewFlight(String code, String dep_time, String arr_time, String dep_loc, String arr_loc, String price) {

        //INSERT INTO `flight` (`code`, `dep_time`, `arr_time`, `dep_loc`, `arr_loc`, `price`, `flight_status`) VALUES ('AB1234', '2021-03-10 23:18:54', '2021-03-21 01:46:24', 'KL', 'KL', '20', NULL)
        String sql = String.format("INSERT INTO `flight` (`code`, `dep_time`, `arr_time`, `dep_loc`, `arr_loc`, `price`, `flight_status`) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', NULL)", code, dep_time, arr_time, dep_loc, arr_loc, price);
//        System.out.println(sql);
        //if row affected more than 1 return true
        return db.execUpdate(sql) > 0;
    }

    //used in modify flight controller
    public static boolean modifyFlight(String columnName, String flightCode, String newData) {

        //UPDATE flight SET `code`='TEST' WHERE code='TES2'
        String sql = String.format("UPDATE flight SET `%s`='%s' WHERE code='%s'", columnName, newData, flightCode);
        return db.execUpdate(sql) > 0;
    }

    //update seat list when creating new bookings
    public static boolean updateSeatList(List<Passenger> passengerArr, String flightCode) {
        boolean flag = false;
        for (Passenger passenger : passengerArr) {
            String selectedSeat = passenger.getSelectedSeat();

            //UPDATE flight_seat SET `is_avail`='0' WHERE seat_code='A0' AND flight_code='TE01'
            String sql = String.format("UPDATE flight_seat SET `is_avail`='0' WHERE seat_code='%s' AND flight_code='%s'", selectedSeat, flightCode);
            if(db.execUpdate(sql) > 0)
                flag = true;
        }
        return flag;
    }

    //modify seat list when updating bookings
    public static boolean modifySeatCode(String oldSeat, String newData, String bookingId) {

//        UPDATE flight_seat SET `is_avail`='1' WHERE seat_code='A5' AND flight_code=(SELECT flight_code FROM bookings WHERE bookings.id='3')
        //UPDATE flight_seat SET `is_avail`='0' WHERE seat_code='A0' AND flight_code=(SELECT flight_code FROM bookings WHERE id=1)
        String sql = String.format("UPDATE flight_seat SET `is_avail`='1' WHERE seat_code='%s' AND flight_code=(SELECT flight_code FROM bookings WHERE bookings.id='%s')",
                oldSeat,
                bookingId);

        //try to remove old seat code
        if(db.execUpdate(sql)>0){

//            UPDATE flight_seat SET `is_avail`='0' WHERE seat_code='A0' AND flight_code=(SELECT flight_code FROM bookings WHERE bookings.id='2')
            String newSeatSql = String.format("UPDATE flight_seat SET `is_avail`='0' WHERE seat_code='%s' AND flight_code=(SELECT flight_code FROM bookings WHERE bookings.id='%s')",
                    newData,
                    bookingId);

            //try to update new seat code
            if (db.execUpdate(newSeatSql)>0)
                return true;
        }

        //if one of it not success
        return false;
    }
}
