package flight.management.lib;

import java.util.Scanner;

public class MyScanner {
    private static final Scanner in = new Scanner(System.in);

    public int nextInt(){
        if (in.hasNextInt()) {
            int input = in.nextInt();
            in.nextLine();
            return input;
        }

        in.nextLine();
        return -1;
    }

    public double nextDouble(){
        double input = 0;

        if(in.hasNextDouble()){
            input = in.nextDouble();
            in.nextLine();
            return input;
        }

        in.nextLine();
        return input;
    }

    public String next(){
        String input = in.next();
        in.nextLine();
        return input;
    }

    public String nextLine(){
        return in.nextLine();
    }

}


