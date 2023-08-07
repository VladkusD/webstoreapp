package bg.webapp.shop.exceptions;

public class CrudValidationException extends RuntimeException{
    public CrudValidationException (String message){
        super(message);
    }
}
