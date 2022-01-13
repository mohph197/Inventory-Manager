package source.exceptions;

public class ObjectDoesntExistException extends Exception{
    public ObjectDoesntExistException() {
        super("User Doesn't Exist!");
    }
}
