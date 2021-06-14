package flight.management.controller;

import flight.management.dao.FlightDao;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class CancelFlightController implements IController {
    private IBoundary ui = UIFactory.getUI("SearchFlight");

    @Override
    public void getData(String... code) {
        FlightDao.cancelFlight(code[0]);
        ui.displaySuccess("Flight cancelled.");
        //reprint selected flight details
        DataPrinter.displayFlight(FlightDao.selectFlightUsingCode(code[0]));
        ui.enterToCon();

        //deselect flight
        LoginController.setSelectedFlight(null);

        //back to manage flight menu
        UIFactory.getUI("ManageFlight").menu();

    }
}
