package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.model.Animal;
import be.ucll.repository.AnimalRepository;
import be.ucll.service.AnimalService;
import be.ucll.service.ServiceException;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {
    
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    private List<Animal> animals = List.of(new Animal("Bella",20), new Animal("Luna",10), new Animal("Muriel", 2), new Animal("Little",1));

    @Test
    public void givenStartAnimals_whenExecutingGetAnimals_ThenAnimalsAreReturned(){
        when(animalRepository.findAll()).thenReturn(animals);

        List<Animal> resultAnimals = animalService.getAnimals();

        assertEquals(animals, resultAnimals);
    }

    @Test
    public void givenUnknownName_whenExecutingGetAnimal_ThenErrorIsThrown(){
        when(animalRepository.findByName(anyString())).thenReturn(null);
        Exception e = assertThrows(ServiceException.class, ()->{
            animalService.getAnimal("Jefke");
        });
        assertEquals("Animal with name Jefke does not exist", e.getMessage());
    }

    @Test
    public void givenKnownName_whenExecutingGetAnimal_thenAnimalIsReturned(){
        when(animalRepository.findByName("Bella")).thenReturn(new Animal("Bella", 20));
        Animal resultAnimal = animalService.getAnimal("Bella");
        assertEquals("Bella", resultAnimal.getName());
        assertEquals(20, resultAnimal.getAge());
    }

    @Test
    public void givenStartAnimals_whenExecutingGetOldestAnimal_thenOldestAnimalIsReturned(){
        when(animalRepository.findFirstByOrderByAgeDesc()).thenReturn(animals.get(0));
        Animal resultAnimal = animalService.getOldestAnimal();
        assertEquals("Bella", resultAnimal.getName());
        assertEquals(20, resultAnimal.getAge());
    }

    @Test
    public void givenStartAnimals_whenExecutingGetAnimalsOlderThan5_thenRightAnimalsAreReturned(){
        List<Animal> expectedAnimals = animals.stream().filter(animal -> animal.getAge()>=5).toList();
        when(animalRepository.findByAgeGreaterThan(5)).thenReturn(expectedAnimals);

        List<Animal> resultAnimals = animalService.getAnimalsOlderThan(5);
        assertEquals(expectedAnimals, resultAnimals);
    }

    @Test
    public void givenNegativeAge_whenExecutingGetAnimalsOlderThan_thenErrorIsThrown(){
        Exception e = assertThrows(ServiceException.class, ()->{
            animalService.getAnimalsOlderThan(-5);
        });
        assertEquals("Age must be a positive integer", e.getMessage());
    }

    @Test
    public void givenValidAnimal_whenExecutingAddAnimal_thenAnimalIsAdded(){
        when(animalRepository.existsByName("Jefke")).thenReturn(false);
        Animal jefke = new Animal("Jefke", 5);
        animalService.addAnimal(jefke);
        verify(animalRepository).save(jefke);
    }

    @Test
    public void givenExistingAnimal_whenExecutingAddAnimal_thenAnimalIsAdded(){
        when(animalRepository.existsByName("Bella")).thenReturn(true);
        Exception e = assertThrows(ServiceException.class,()->{
            animalService.addAnimal(new Animal("Bella", 5));
        });
        assertEquals("This name is already in the database", e.getMessage());
    }
}
