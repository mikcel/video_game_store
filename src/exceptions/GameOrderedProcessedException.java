package exceptions;

public class GameOrderedProcessedException extends Exception {

    public GameOrderedProcessedException(){
        super("Game has already been added for that order");
    }

}
