package exceptions;

public class GameNotFoundException extends Exception {

    public GameNotFoundException(){
        super("Requested game not found");
    }

}
