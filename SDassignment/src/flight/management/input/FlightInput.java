package flight.management.input;

import flight.management.dao.FlightDao;
import flight.management.entity.Flight;
import flight.management.lib.DateUtil;
import flight.management.lib.IBoundary;

import java.time.LocalDateTime;

public class FlightInput extends BaseInput{
    private LocalDateTime tempDepTime;
    private LocalDateTime tempArrTime;

    public static final String FLIGHT_CODE_FORMAT = "^[A-Z]{2}[0-9]{4}$";  //sample: BA2490

    public String nextFlightCode(){
        while (true){
            String data;

            ui.promptInput("Enter flight code (AA1234): ");
            data = in.nextLine();

            if (data.equals("0"))
                return data;

            if (data.matches(FLIGHT_CODE_FORMAT))
                if(FlightDao.selectFlightUsingCode(data) == null)
                    return data;
                else
                    ui.displayError("Flight Code existed. Please try another one.");
            else
                ui.displayError("Not correct flight code format. Please Enter again.");

        }
    }

    public String modifyFlightCode(){
        while (true){
            String data;

            ui.promptInput("Enter new flight code (AA1234): ");
            data = in.nextLine();

            if (data.matches(FLIGHT_CODE_FORMAT))
                if(FlightDao.selectFlightUsingCode(data) == null)
                    return data;
                else
                    ui.displayError("Flight Code existed. Please try another one.");
            else
                ui.displayError("Not correct flight code format. Please Enter again.");

        }
    }

    public String nextArrLocation(){
        ui.promptInput("Enter Arrival Location: ");
        return in.nextLine();
    }

    public String nextDepLocation(){
        ui.promptInput("Enter Departure Location: ");
        return in.nextLine();
    }

    public String nextDepTime(){
        while (true){
            ui.promptInput("Enter departure time (YYYY-MM-DD HH:mm): ");
            String date = in.nextLine();
            try {
                //try to parse date time if parse success return date
                tempDepTime = DateUtil.stringToDateTime(date);

                return date;
            }catch (Exception e){
                ui.displayError("Incorrect Date format. Please enter again.");
            }
        }
    }

    public String modifyDepTime(Flight selectedFlight){
        while (true){
            ui.promptInput("Enter new departure time (YYYY-MM-DD HH:mm): ");
            String newDate = in.nextLine();
            try {
                //try to parse date time if parse success return date
                LocalDateTime newDep = DateUtil.stringToDateTime(newDate);
                tempArrTime = selectedFlight.getArrivalTime();

                if(newDep.isAfter(tempArrTime)){
                    throw new IllegalArgumentException();
                }
                return newDate;
            }catch (IllegalArgumentException e){
                ui.displayError("Departure date cannot late than arrival date. Please enter again.");
            }
            catch (Exception e){
                ui.displayError("Incorrect Date format. Please enter again.");
            }
        }
    }

    public String nextArrTime(){
        while (true){
            ui.promptInput("Enter new arrival time (YYYY-MM-DD HH:mm): ");
            String date = in.nextLine();
            try {
                //try to parse date time if parse success return date
                LocalDateTime tempArrTime = DateUtil.stringToDateTime(date);

                //If departure date is after arrival time
                if (tempDepTime.isAfter(tempArrTime))
                    throw new IllegalArgumentException();

                return date;
            }catch (IllegalArgumentException e){
                ui.displayError("Arrival Time cannot early than departure time. Please enter again.");
            } catch (Exception e){
                ui.displayError("Incorrect Date format. Please enter again.");
            }
        }
    }

    public String modifyArrTime(Flight selectedFlight){
        while (true){
            ui.promptInput("Enter arrival time (YYYY-MM-DD HH:mm): ");
            String newDate = in.nextLine();
            try {
                //try to parse date time if parse success return date
                LocalDateTime newArr = DateUtil.stringToDateTime(newDate);
                tempDepTime = selectedFlight.getDepartureTime();

                if(newArr.isBefore(tempDepTime)){
                    throw new IllegalArgumentException();
                }
                return newDate;
            }catch (IllegalArgumentException e){
                ui.displayError("Arrival date cannot early than departure date. Please enter again.");
            }
            catch (Exception e){
                ui.displayError("Incorrect Date format. Please enter again.");
            }
        }
    }

    public double nextPrice(){
        while (true){
            ui.promptInput("Enter ticket price (RM): ");
            double price = in.nextDouble();
            try {
                if (price <= 0)
                    throw new IllegalArgumentException();

                return price;
            }catch (IllegalArgumentException e){
                ui.displayError("Price format is not correct.");
            }
        }
    }


    public String nextDate(){
        while (true){
        ui.promptInput("Enter date (YYYY-MM-DD): ");
        String date = in.nextLine();
            try {
                //try to parse date if parse success return date
                DateUtil.stringToDate(date);
                return date;
            }catch (Exception e){
                ui.displayError("Not correct Date format. Please enter again.");
            }
        }
    }

    public FlightInput(IBoundary ui) {
        super(ui);
    }
}
