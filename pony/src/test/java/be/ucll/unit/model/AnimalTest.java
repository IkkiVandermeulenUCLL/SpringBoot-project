package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.ucll.model.Animal;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class AnimalTest {
    private static Validator validator;
    private static ValidatorFactory validatorFactory;
    
    @BeforeAll
    public static void createValidator(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @Test
    public void givenValidNameAndAge_whenCreatingAnimal_ThenAnimalIsCreated(){
        Animal animal = new Animal("Bella",20);
        assert "Bella" == animal.getName();
        assert 20 == animal.getAge();
    }

    @Test
    public void givenNullName_whenCreatingAnimal_thenErrorIsThrown(){
        Animal animal = new Animal(null, 20);
        Set<ConstraintViolation<Animal>> violations = validator.validate(animal);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test 
    public void givenAgeOverLimit_whenCreatingAnimal_thenErrorIsThrown(){
        Animal animal = new Animal("Bella", 51);
        Set<ConstraintViolation<Animal>> violations = validator.validate(animal);
        assertEquals(1, violations.size());
        assertEquals("Age cannot be greater than 50", violations.iterator().next().getMessage());
    }

    @Test 
    public void givenAgeUnderLimit_whenCreatingAnimal_thenErrorIsThrown(){
        Animal animal = new Animal("Bella", 0);
        Set<ConstraintViolation<Animal>> violations = validator.validate(animal);
        assertEquals(1, violations.size());
        assertEquals("Age cannot be smaller than 1", violations.iterator().next().getMessage());
    }
}
