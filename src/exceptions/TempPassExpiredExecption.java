package exceptions;

public class TempPassExpiredExecption extends Exception {

    public TempPassExpiredExecption(){
        super("Temporary Password has expired. Please ask for a new one if necessary!");
    }
}
