package creditcardvalidator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class CreditCardValidator {

    public static boolean validateCreditCard(long ccNum, int ccCvv, Date expiryDate) throws Exception {

        // Check that Credit card number starts by 50,51,52,53,54 or 55 and is 16 digits long
        Pattern ccNumPatrn = Pattern.compile("^5[0-5]\\d{14}$");
        if (!ccNumPatrn.matcher(Long.toString(ccNum)).find()){
            throw new Exception("Credit Card Number is invalid!");
        }

        // Check that the credit card cvv is 3 digits long
        Pattern ccCvvPatrn = Pattern.compile("^\\d{3}$");
        if (!ccCvvPatrn.matcher(Integer.toString(ccCvv)).find()){
            throw new Exception("Credit Card CVV is invalid!");
        }


        // Check that the the expiry date is before the current date
        Calendar cal = Calendar.getInstance();
        cal.setTime(expiryDate);

        Calendar currentCal = Calendar.getInstance();

        if (cal.get(Calendar.MONTH) <= currentCal.get(Calendar.MONTH) && cal.get(Calendar.YEAR) <= currentCal.get(Calendar.YEAR)){
            throw new Exception("Credit Card Expiry Date is invalid!");
        }

        return true;

    }

}
