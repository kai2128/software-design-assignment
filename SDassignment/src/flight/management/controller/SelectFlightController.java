package flight.management.controller;

import flight.management.dao.FlightDao;
import flight.management.entity.Flight;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class SelectFlightController implements IController {
    private IBoundary ui = UIFactory.getUI("SearchFlight");
    @Override
    public void getData(String...flightCode) {
        Flight result = FlightDao.selectFlightUsingCode(flightCode[0]);


        if(result != null){
            LoginController.setSelectedFlight(result);
            ui.displaySuccess("Flight Selected: ");
            LoginController.displaySelectedFlight();
            ui.enterToCon();

            LoginController.displayLoggedInMenu();
        }else {
            ui.displayWarning("No result found!");
            ui.menu();
        }

    }
}
