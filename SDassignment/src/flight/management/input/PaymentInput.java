package flight.management.input;

import flight.management.lib.IBoundary;

public class PaymentInput extends BaseInput{

    //contains only alphabert and between 5 - 15 words
    public static final String CARD_RULE = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$";
    public static final String EXPIRY_DATE_RULE = "^[0-1][0-9]\\/[2-3][0-9]$";
    public static final String CVV_RULE = "^[0-9]{3}$";




    public String nextCardNumber(){
        return nextFirstData("Enter bank card (0 to cancel booking) (1234-1234-1234-1234): ",
                "Invalid card number",
                CARD_RULE);
    }


    public String nextCardName(){
        return nextData("Enter name on card: ", "Invalid card name.", ".*");
    }

    public String nextExpiryDate(){
        return nextData("Enter expiry date (mm/yy): ", "Invalid expiry date!", EXPIRY_DATE_RULE);
    }

    public String nextCVV(){
        return nextData("Enter card CVV: ", "Invalid CVV!", CVV_RULE);
    }
    public String nextTAC(){
        return nextData("Enter TAC code: ", "Invalid TAC!", ".*");
    }


    public PaymentInput(IBoundary ui) {
        super(ui);
    }
}
