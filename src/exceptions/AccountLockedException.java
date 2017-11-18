package exceptions;

public class AccountLockedException extends Exception{

    public AccountLockedException(){super("Account has been locked. Contact Admin!");}

}
