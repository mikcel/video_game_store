package exceptions;

public class OutOfStockException extends Exception {

    public OutOfStockException(String itemName){
        super("Quantity asked for game: " + itemName + " cannot be satisfied due to insufficient quantity in stock!");
    }

}
