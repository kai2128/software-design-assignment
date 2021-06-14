package flight.management.dao;

import flight.management.entity.Bookings;
import flight.management.entity.Passenger;

public class PassengerDao extends BaseDao {


    public static boolean addPassengerFromNewBookings(Bookings newBookings, String newBookingsId){
        boolean flag = false;

//        INSERT INTO `passenger` (`book_id`, `p_name`, `p_ic`, `p_email`, `p_phone`, `selected_seat`, `ticket_price`) VALUES ('', '', '', '', '', '', '', '')
        for (Passenger passenger : newBookings.getPassengerArr()) {
            String sql = String.format("INSERT INTO passenger VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%,2f')",
                    newBookingsId,
                    passenger.getName(),
                    passenger.getIc(),
                    passenger.getEmail(),
                    passenger.getPhone(),
                    passenger.getSelectedSeat(),
                    passenger.getTicketPrice());
            if(db.execUpdate(sql)> 0){
                flag =true;
            }
        }
        return flag;
    }


}
