public class EventException extends RuntimeException {   //created own exception by extending an unchecked exception class
    public EventException(String message){     //exception obj will output what it is told when called
        super(message);
    }
}
