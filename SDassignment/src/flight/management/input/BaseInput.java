package flight.management.input;

import flight.management.lib.IBoundary;
import flight.management.lib.MyScanner;

public abstract class BaseInput {
    protected static final MyScanner in = new MyScanner();
    protected IBoundary ui;

    public String nextData(String inputMsg, String errMessage, String regex){
        while(true){
            String data;
            ui.promptInput(inputMsg);
            data = in.nextLine();

            if (data.matches(regex) && !(data.equals("")))
                return data;

            ui.displayError(errMessage);
        }

    }

    public int nextChoice(int lower, int upper){
        int choice = -1;

        //while choice not equal to valid range
        while (true){
            ui.promptInput("Enter choice: ");
            choice = in.nextInt();

            if (choice >= lower && choice <= upper)
                return choice;

            ui.displayError("Invalid option");
        }
    }

    public int nextChoice(int upper){
        return nextChoice(0,upper);
    }


    //if first input is 0 then return to upper menu
    public String nextFirstData(String inputMsg, String errMessage, String regex){
        while (true){
            String data;

            ui.promptInput(inputMsg);
            data = in.nextLine();

            if (data.equals("0") || data.matches(regex))
                return data;
            ui.displayError(errMessage);
        }
    }

    public BaseInput(IBoundary ui) {
        this.ui = ui;
    }
}
