package flight.management.boundary;

import flight.management.input.FlightInput;
import flight.management.lib.ControllerFactory;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class CreateFlightUI implements IBoundary {
    private FlightInput input = new FlightInput(this);

    private String code;
    private String dep_time;
    private String arr_time;
    private String dep_loc;
    private String arr_loc;
    private double price;

    @Override
    public void menu() {
        displayTitle("Create flight");
        code = input.nextFlightCode();
        if(code.equals("0")){
            UIFactory.getUI("ManageFlight").menu();
        }

        dep_time = input.nextDepTime();
        arr_time = input.nextArrTime();

        promptInput("Enter Departure Location: ");
        dep_loc = in.nextLine();

        promptInput("Enter Arrival Location: ");
        arr_loc = in.nextLine();

        price = input.nextPrice();

        ControllerFactory.getController("CreateFlight").getData(code, dep_time, arr_time, dep_loc, arr_loc, String.valueOf(price));

    }
}
