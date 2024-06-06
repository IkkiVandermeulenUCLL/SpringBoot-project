package be.ucll.model;

public class DomainException extends RuntimeException{
    public DomainException(String argument){
        super(argument);
    }
}
