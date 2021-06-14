package flight.management.entity;

import java.time.LocalDate;
import java.util.Map;

public class Passenger {
    private String name;
    private String ic;
    private String email;
    private String phone;
    private String selectedSeat;
    private double ticketPrice;

    public String getName() {
        return name;
    }

    public String getIc() {
        return ic;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setTicketPrice(double price) {
//        int ic = 20;
//        int ioc20 = 21;
//        int yob= 0;
//        if(ic >= 30){
//            yob = ic + 1900;
//        }else
//            yob = ic + 2000;
        LocalDate year = LocalDate.now();
        //2021
        int currentYear = year.getYear();
        int icYear = Integer.parseInt(ic.substring(0,2));
        int yob = 0;
        if(icYear >= 30){
            yob = icYear + 1900;
        }else
            yob = icYear + 2000;
        int age = currentYear-yob;

        //50% off for kid <= 12 years old or senior citizens >= 60 years old
        if(age <= 12){
            price = price*0.5;
        }else if(age >= 60){
            price = price*0.5;
        }
        this.ticketPrice = price;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String[] toTable(){
        return new String[]{
          name,
          ic,
          email,
          phone,
          selectedSeat,
          String.valueOf(ticketPrice)
        };
    }

    public String toNumberedTable(){
        return String.format("%s,%s,%s,%s,%s,%s",
                    name,
                    ic,
                    email,
                    phone,
                    selectedSeat,
                    String.valueOf(ticketPrice)
        );

    }

    //need to match with create bookings ui
    public Passenger(String[] psgData){
        this(psgData[0],
            psgData[1],
            psgData[2],
            psgData[3],
            psgData[4],
            psgData[5]
            );
    }

    //from database
    public Passenger(Map<String, String> result) {
        this.name = result.get("p_name");
        this.ic = result.get("p_ic");
        this.email = result.get("p_email");
        this.phone = result.get("p_phone");
        this.selectedSeat = result.get("selected_seat");
        this.ticketPrice = Double.parseDouble(result.get("ticket_price"));
    }


//from client input
    public Passenger(String name, String ic, String email, String phone, String selectedSeat, String price) {
        this.name = name;
        this.ic = ic;
        this.email = email;
        this.phone = phone;
        this.selectedSeat = selectedSeat;
        setTicketPrice(Double.parseDouble(price));
    }

}
