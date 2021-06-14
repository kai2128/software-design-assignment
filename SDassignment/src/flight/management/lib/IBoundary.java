package flight.management.lib;


public interface IBoundary {
    public MyScanner in = new MyScanner();

    //FOR UI DISPLAY MESSAGE
    default void blankLine(){
        System.out.println("");
    }

    default void line(){
        System.out.println("---------------------");
    }

    default void displayTitle(String title){
        line();
        System.out.println(title);
        line();
    };

    default void displayOption(String option){
        System.out.println(String.format("   %s", option));
    };

    default void displayError(String err){
        System.out.println("\u001B[31m" +  err +   "\u001B[0m");
    };
    default void displayWarning(String warning){
        System.out.println("\u001B[33m" +  warning +   "\u001B[0m");
    };
    default void displaySuccess(String success){
        System.out.println("\u001B[32m" +  success +   "\u001B[0m");
    };
    default void enterToCon(){
        System.out.println("Press enter to continue...");
        in.nextLine();
    }
    default void displayMsg(String msg){
        System.out.println("* " + msg +  " *");
    };
    default void promptInput(String msg){ System.out.print(msg);};



    //UI MAIN MENU
    void menu();

}
