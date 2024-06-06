package be.ucll.service;

public class ServiceException extends RuntimeException{
    public ServiceException(String argument){
        super(argument);
    }
}
