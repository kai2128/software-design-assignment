package flight.management.boundary;

import flight.management.dao.FlightDao;
import flight.management.entity.Flight;
import flight.management.input.MenuInput;
import flight.management.lib.DataPrinter;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

import java.util.List;

public class ViewAllFlightUI implements IBoundary {
    private MenuInput input = new MenuInput(this);

    @Override
    public void menu() {
        int choice = -1;
        int currentPage = 1;

        displayTitle("View All Flight");

        List<Flight> allFlight = FlightDao.getAllFlight();
        DataPrinter.displayFlightSearchResults(allFlight);

        enterToCon();
        UIFactory.getUI("AdminMenu").menu();

    }
}
