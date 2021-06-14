package flight.management.controller;

import flight.management.dao.FlightDao;
import flight.management.lib.IBoundary;
import flight.management.lib.IController;
import flight.management.lib.UIFactory;

public class ModifyFlightController implements IController {
    IBoundary ui = UIFactory.getUI("ModifyFlight");

    @Override
    public void getData(String... data) {
        String choice = data[0];
        String currentFlightCode = LoginController.getSelectedFlight().getFlightCode();
        String newFlightCode = null;
        boolean isModified = false;

        switch (choice){
            case "1" -> {
                newFlightCode = data[1];
                isModified = FlightDao.modifyFlight("code", currentFlightCode, data[1]);
                LoginController.reSelectFlight(newFlightCode);
            }
            case "2" -> isModified = FlightDao.modifyFlight("dep_time", currentFlightCode, data[1]);
            case "3" -> isModified = FlightDao.modifyFlight("arr_time", currentFlightCode, data[1]);
            case "4" -> isModified = FlightDao.modifyFlight("dep_loc", currentFlightCode, data[1]);
            case "5" -> isModified = FlightDao.modifyFlight("arr_loc", currentFlightCode, data[1]);
            case "6" -> isModified = FlightDao.modifyFlight("price", currentFlightCode, data[1]);
            case "7" -> isModified = FlightDao.modifyFlight("flight_status", currentFlightCode, data[1]);
        }

        if(isModified){
            ui.displaySuccess("Details updated.");

            if(newFlightCode == null)
                //if no new flight code set, select flight using current flight code
                LoginController.reSelectFlight(currentFlightCode);
            LoginController.displaySelectedFlight();
            ui.enterToCon();
        } else {
            ui.displayError("Not updated.");
            ui.enterToCon();
        }


        //Back to admin menu
        UIFactory.getUI("AdminMenu").menu();


    }
}
