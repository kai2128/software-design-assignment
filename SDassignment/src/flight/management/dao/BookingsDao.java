package flight.management.dao;

import flight.management.entity.Bookings;
import flight.management.entity.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingsDao extends BaseDao {


    public static boolean modifyPassenger(String bookingId, String ic, String newData, String columnName){

//        UPDATE passenger SET `p_name`='asb' WHERE book_id='1' AND p_ic='123456-12-1234'
        String sql = String.format("UPDATE passenger SET `%s`='%s' WHERE book_id='%s' AND p_ic='%s'", columnName, newData, bookingId, ic);
        // if update seat code need to update seat in flight list

        if(columnName.equals("selected_seat")){
            String oldSeatSql = String.format("SELECT selected_seat FROM passenger WHERE book_id='%s' AND p_ic='%s'", bookingId, ic);
            Map<String, String> oldSeat = db.selectOne(oldSeatSql);
            FlightDao.modifySeatCode(oldSeat.get("selected_seat"), newData, bookingId);
        }

        return db.execUpdate(sql)>0;
    }

    public static List<Bookings> selectBookingsUsingFlightCode(String flightCode){
        String sql = "SELECT * FROM bookings WHERE flight_code='" + flightCode + "'";
        try {
            return convertBookings(db.selectAll(sql));
        }catch (Exception e){
            return null;
        }
    }

    public static boolean createNewBookings(Bookings newBookings){
//        INSERT INTO `bookings` (`id`, `flight_code`, `c_id`, `card_number`, `total_price`, `book_status`) VALUES ('', '', '', '', '', NULL, NULL)
//        INSERT INTO bookings VALUES  (NULL, 'TE01', '1', '1234', '20', 'BOOKED')
        String sql = String.format("INSERT INTO bookings VALUES  (NULL, '%s', '%s', '%s', '%.2f', 'BOOKED')",newBookings.getFlight().getFlightCode(), newBookings.getClientId(), newBookings.getCardNumber(), newBookings.getTotalPrice());
        if(db.execUpdate(sql) > 0){
            String selectNewRecord = "SELECT id FROM bookings ORDER BY id DESC LIMIT 1";
            Map<String, String> bookings = db.selectOne(selectNewRecord);

            if(PassengerDao.addPassengerFromNewBookings(newBookings, bookings.get("id")))
                if(FlightDao.updateSeatList(newBookings.getPassengerArr(), newBookings.getFlight().getFlightCode()))
                    return true;
            return false;
        }
        return false;
    }

    //select all bookings
    public static List<Bookings> getAllBookings(){
        return getClientBookings("0 OR 1=1");
    }


    public static List<Bookings> getClientBookings(String clientId) {
        //SELECT * FROM `bookings` WHERE `c_id`=1
        String sql = "SELECT * FROM bookings WHERE `c_id`="+clientId;
        return convertBookings(db.selectAll(sql));
    }
    //convert db result to bookings
    private static List<Bookings> convertBookings(List<Map<String, String>> results){
        List<Bookings> tempList = new ArrayList<>();
        for (Map<String, String> result : results) {
            //add passenger to bookings
            tempList.add(new Bookings(result, addPassengerList(result.get("id"))));
        }
        return tempList;
    }

    //for convert db result to passenger list
    public static List<Passenger> convertPassenger(List<Map<String, String>> results){
        List<Passenger> tempList = new ArrayList<>();

        for (Map<String, String> result : results) {
            tempList.add(new Passenger(result));
        }

        return tempList;
    }

    //add passenger list to bookings
    public static List<Passenger> addPassengerList(String bookId){
        String sql = "SELECT * FROM passenger WHERE `book_id`="+bookId;

        return convertPassenger(db.selectAll(sql));
    }

    public static Bookings selectBookings(String id){
        String sql = "SELECT * FROM bookings WHERE id='" + id + "'";
        try {
            return convertBookings(db.selectAll(sql)).get(0);
        }catch (Exception e){
            return null;
        }
    }


    public static List<Bookings> searchBookings(String id) {
        //select * from bookings where cast(id as char) like '%5%'
        String sql = "SELECT * FROM bookings WHERE cast(id as char) LIKE '%" + id + "%'" ;
        return convertBookings(db.selectAll(sql));
    }

    public static boolean cancelBookings(String id) {
//        UPDATE `bookings` SET `book_status`='CANCELLED' WHERE id=3
        String sql = "UPDATE `bookings` SET `book_status`='CANCELLED' WHERE id=" + id;
        return db.execUpdate(sql)>0;
    }
}
