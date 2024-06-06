package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import be.ucll.model.Animal;
import be.ucll.model.DomainException;
import be.ucll.model.Stable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class StableTest {
    private static Validator validator;

    private static ValidatorFactory validatorFactory;

    private Stable stable = new Stable("Stable", 5);
    private Animal animal = new Animal("Bella", 5);

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
    public void givenValidStable_whenCreatingStable_thenStableIsCreated(){
        assertEquals("Stable", stable.getName());
        assertEquals(5, stable.getCapacity());
        assertEquals(0, stable.getAnimals().size());
    }

    @Test 
    public void givenNullName_whenCreatingStable_thenErrorIsThrown(){
        stable.setName(null);
        Set<ConstraintViolation<Stable>> violations = validator.validate(stable);
        assertEquals(1, violations.size());
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void givenNegativeCapacity_whenCreatingStable_thenErrorIsThrown(){
        stable.setCapacity(-1);
        Set<ConstraintViolation<Stable>> violations = validator.validate(stable);
        assertEquals(1, violations.size());
        assertEquals("Capacity must be a positive integer", violations.iterator().next().getMessage());
    }

    @Test
    public void given5Capacity_whenAddingAnimal_thenAnimalIsAddedAndCapacityDecreases(){
        stable.addAnimal(animal);
        Animal addedAnimal = stable.getAnimals().get(0);
        assertEquals(4, stable.getCapacity());
        assertEquals(1, stable.getAnimals().size());
        assertEquals("Bella", addedAnimal.getname());
        assertEquals(5, addedAnimal.getage());
    }

    @Test
    public void givenFullStable_whenAddingAnimal_thenErrorIsThrown(){
        stable.setCapacity(0);
        Exception e = assertThrows(DomainException.class, ()->{
            stable.addAnimal(animal);
        });
        assertEquals("Not enough space", e.getMessage());
    }
}
