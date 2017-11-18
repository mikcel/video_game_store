package exceptions;

public class UserNotExistsException extends Exception {

    public UserNotExistsException(){
        super("User does not exist!");
    }

}
