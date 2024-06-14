package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.model.Address;
import be.ucll.model.Animal;
import be.ucll.model.DomainException;
import be.ucll.model.Stable;
import be.ucll.repository.StableRepository;
import be.ucll.service.AddressService;
import be.ucll.service.AnimalService;
import be.ucll.service.ServiceException;
import be.ucll.service.StableService;

@ExtendWith(MockitoExtension.class)
public class StableServiceTest {
    
    @Mock
    private StableRepository stableRepository;

    @Mock 
    private AddressService addressService;

    @Mock
    private AnimalService animalService;
    
    @InjectMocks
    private StableService stableService;
    
    private Animal animal = new Animal("Jefke", 5);
    private Stable stable = new Stable("Stable", 5);
    private Address address = new Address("Naamsestraat", 20, "Leuven");
    
    @Test
    public void givenValidAnimalAndStable_whenExecutingAddStable_thenStableIsReturned(){
        when(stableRepository.save(stable)).thenReturn(stable);
        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        Stable expectedStable = stable;
        expectedStable.addAnimal(animal);
        Stable resultStable = stableService.addStable(animal.getName(), stable);
        assertEquals(expectedStable, resultStable);
    }

    @Test
    public void givenValidAnimalAndStableWith0Capacity_whenExecutingAddStable_thenErrorIsThrown(){
        stable.setCapacity(0);
        when(stableRepository.save(stable)).thenReturn(stable);
        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        Exception e = assertThrows(DomainException.class,()->{
            stableService.addStable(animal.getName(), stable);
        });
        assertEquals("Not enough space", e.getMessage());
    }

    @Test
    public void givenInvalidAnimalAndValidStable_whenExecutingAddStable_thenErrorIsThrown(){
        when(animalService.getAnimal(animal.getName())).thenThrow(new ServiceException(String.format("Animal with name %s does not exist", animal.getName())));
        Exception e = assertThrows(ServiceException.class, ()->{
            stableService.addStable(animal.getName(), stable);
        });
        assertEquals(String.format("Animal with name %s does not exist", animal.getName()), e.getMessage());
    }

    @Test
    public void givenValidAnimalAndValidStableId_whenExecutingAddAnimal_thenAnimalIsAdded(){
        stable.setId(1L);
        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        when(stableRepository.findById(stable.getId())).thenReturn(Optional.of(stable));
        when(stableRepository.save(stable)).thenReturn(stable);
        Stable resultStable = stableService.addAnimal(animal.getName(), Optional.of(1L));
        assertEquals(stable, resultStable);
    }

    @Test
    public void givenValidAnimalAndUnknownStableId_whenExecutingAddAnimal_thenErrorIsThrown(){
        stable.setId(1L);
        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        when(stableRepository.findById(stable.getId())).thenReturn(Optional.empty());
        Exception e = assertThrows(ServiceException.class, ()->{
            stableService.addAnimal(animal.getName(), Optional.of(1L));
        });
        assertEquals(String.format("Stable with id %s does not exist", 1L), e.getMessage());
    }

    @Test
    public void givenValidAnimalWithStable_whenExecutingGetStableOfAnimal_thenStableIsReturned(){
        animal.setStable(stable);

        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        assertEquals(stable, stableService.getStableOfAnimal(animal.getName()));
    }

    @Test
    public void givenValidAnimalWithoutStable_whenExecutingGetStableOfAnimal_thenErrorIsThrown(){
        when(animalService.getAnimal(animal.getName())).thenReturn(animal);
        Exception e = assertThrows(ServiceException.class,()->{
            stableService.getStableOfAnimal(animal.getName());
        });
        assertEquals("Animal has no stable", e.getMessage());
    }

}
