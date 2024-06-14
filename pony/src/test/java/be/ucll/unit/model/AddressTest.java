package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.ucll.model.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class AddressTest {
    
    private Address address = new Address("Naamsestraat", 20, "Leuven");
    
    private static Validator validator;

    private static ValidatorFactory validatorFactory;

    @BeforeAll
    private static void setUp(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    private static void cleanUp(){
        validatorFactory.close();
    }

    @Test
    public void givenValidAddress_whenInitiatingAddress_thenAddressIsInitiated(){
        assertEquals("Naamsestraat", address.getStreet());
        assertEquals(20, address.getNumber());
        assertEquals("Leuven", address.getPlace());
    }

    @Test
    public void givenNullStreet_whenInitiatingAddress_thenErrorIsThrown(){
        address.setStreet(null);
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(1, violations.size());
        assertEquals("Street is required", violations.iterator().next().getMessage());
    }    

    @Test
    public void givenInvalidStreet_whenInitiatingAddress_thenErrorIsThrown(){
        address.setStreet("       ");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(1, violations.size());
        assertEquals("Street is required", violations.iterator().next().getMessage());
    }

    @Test
    public void givenNegativeNumber_whenInitiatingAddress_thenErrorIsThrown(){
        address.setNumber(-5);
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(1, violations.size());
        assertEquals("Number must be a positive integer", violations.iterator().next().getMessage());
    }

    @Test
    public void givenNullPlace_whenInitiatingAddress_thenErrorIsThrown(){
        address.setPlace(null);
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(1, violations.size());
        assertEquals("Place is required", violations.iterator().next().getMessage());
    }    

    @Test
    public void givenInvalidPlace_whenInitiatingAddress_thenErrorIsThrown(){
        address.setPlace("       ");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertEquals(1, violations.size());
        assertEquals("Place is required", violations.iterator().next().getMessage());
    }
}
