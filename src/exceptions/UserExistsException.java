package exceptions;

public class UserExistsException  extends Exception{

    public UserExistsException(){
        super("User Exists. Email already in use!");
    }

}
