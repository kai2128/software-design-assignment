package flight.management.input;

import flight.management.dao.UserDao;
import flight.management.lib.IBoundary;

public class UserInput extends BaseInput{

    //contains only alphabert and between 5 - 15 words
    public static final String USERNAME_RULE = "^[a-zA-Z0-9]{5,15}$";
    public static final String NAME_RULE = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String EMAIL_RULE = "^\\S+@\\S+$";
    public static final String PHONE_RULE = "^[0-9]{8,12}$";
    public static final String PASSWORD_RULE = "^[\\w]{4,10}$";

    public double nextDouble(){
        while (true){
            ui.promptInput("Enter a double: ");
            double aDouble = in.nextDouble();

            if (aDouble > 0)
                return aDouble;

            ui.displayError("No a number");
        }
    }


    public String nextUsername(){
        while (true){
            String data;

            ui.promptInput("Enter username: ");
            data = in.nextLine();

            if (data.equals("0")) //back to upper menu if username is 0
                return data;

            if(data.matches(USERNAME_RULE)) // check is username existed
                if(UserDao.isUsernameRepeated(data) == false){
                    return data;
                }
                else
                    ui.displayError("Username existed. Please try another one.");
            else
            ui.displayError("Invalid username can only contains alphanumeric and between 5 to 15 words.");
        }
    }

    public String nextName(){
        return nextData("Enter name: ", "Invalid name!", NAME_RULE);
    }

    public String nextPhone(){
        return nextData("Enter phone: ", "Invalid phone number (cannot containes special character)!", PHONE_RULE);
    }


    public String nextEmail(){
        return nextData("Enter email: ", "Invalid email!", EMAIL_RULE);
    }

    public String nextPassword(){
        return nextData("Enter password: ", "Invalid password!", PASSWORD_RULE);
    }


    public UserInput(IBoundary ui) {
        super(ui);
    }
}
