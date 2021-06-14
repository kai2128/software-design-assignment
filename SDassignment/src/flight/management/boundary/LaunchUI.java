package flight.management.boundary;

import flight.management.input.MenuInput;
import flight.management.lib.Database;
import flight.management.lib.IBoundary;
import flight.management.lib.UIFactory;

public class LaunchUI implements IBoundary {
    private MenuInput input = new MenuInput(this);
    private int choice;

    static{
        //check with database connection
        try {
            Database.getInstance();
            System.out.println("Connection to database success.");
        } catch (Exception e){
            System.out.println("Connection to database failed...");
            System.out.println("Please try to check with database configuration in the db_config.properties ");
            System.out.println();
            System.exit(1);
        }
    }

    @Override
    public void menu() {
        choice = -1;
        System.out.println();
        System.out.println();

        System.out.println("               __|__\n" +
                           "         *---o--(_)--o---*");

        System.out.println("------------------------------------");
        System.out.println("       Flight Management System     ");
        System.out.println("                             v 1.0.6");
        System.out.println("------------------------------------");
        displayOption("1. Login account");
        displayOption("2. Signup account");
        displayOption("3. Exit");

        choice = input.nextChoice(1,3);

        switch (choice) {
            case 1 -> UIFactory.getUI("Login").menu();
            case 2 -> UIFactory.getUI("SignUp").menu();
            case 3 -> {
                System.exit(1);
            }
        }
    }

}
