package exceptions;

public class IncorrectPasswordException extends Exception {

    public IncorrectPasswordException(){super("Incorrect Password entered!");}

    public IncorrectPasswordException(String s) {
        super(s);
    }
}
