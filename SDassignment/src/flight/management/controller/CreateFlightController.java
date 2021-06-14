package flight.management.controller;

import flight.management.dao.FlightDao;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class CreateFlightController implements IController {
    private IBoundary ui = UIFactory.getUI("CreateFlight");


    @Override
    public void getData(String... flightData) {

        if(FlightDao.insertNewFlight(
          flightData[0],
          flightData[1],
          flightData[2],
          flightData[3],
          flightData[4],
          flightData[5]
        )){
            ui.displaySuccess("New Flight Created");

            //use new code to display flight details
            DataPrinter.displayFlight(FlightDao.selectFlightUsingCode( flightData[0]));
        }else {
            ui.displayError("Failed to create new flight");
        }

        ui.enterToCon();
        //return to main menu
        UIFactory.getUI("ManageFlight").menu();
    }
}
