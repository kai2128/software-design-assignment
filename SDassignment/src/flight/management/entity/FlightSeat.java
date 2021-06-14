package flight.management.entity;

import java.util.Map;

public class FlightSeat {
    private String seatCode;
    private boolean availability;

    public String getSeatCode() {
        return seatCode;
    }

    public boolean isAvailable() {
        return availability;
    }

    public FlightSeat(Map<String, String> seatData) {
        this.seatCode = seatData.get("seat_code");
        this.availability = seatData.get("is_avail").equals("1");
    }
}
