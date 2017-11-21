package exceptions;

public class GameInCartException extends Exception {

    public GameInCartException(){
        super("Game already in shopping cart");
    }

}
