package flight.management.controller;

import flight.management.boundary.SearchFlightUI;
import flight.management.dao.FlightDao;
import flight.management.entity.Flight;
import flight.management.lib.*;

import java.util.List;

public class SearchFlightController implements IController {

    private IBoundary ui = UIFactory.getUI("SearchFlight");

    @Override
    public void getData(String... searchFlightData) {

        //search using date, loc
        if(searchFlightData.length > 2){
            List<Flight> result = FlightDao.searchFlightUsingLoc(searchFlightData);

            if(!(result.isEmpty())){
                ui.displaySuccess("Result found: ");
                DataPrinter.displayFlightSearchResults(result);
                ((SearchFlightUI)ui).displayActionOption();
            }else {
                ui.displayMsg("No result found.");
                ui.blankLine();
                ui.menu();
            }

        }
        //search using flight code
        else {
            //go to select flight using flight code
            ControllerFactory.getController("SelectFlight").getData(searchFlightData[0]);
        }
    }


}
