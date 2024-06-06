package be.ucll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Animal;
import be.ucll.repository.AnimalRepository;

@Service
public class AnimalService {
    private AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAnimals(){
        return animalRepository.findAll();
    }

    public Animal addAnimal(Animal animal){
        if(animalRepository.existsByName(animal.getname())){
            throw new ServiceException("This name is already in the database");
        }
        return animalRepository.save(animal);
    }

    public List<Animal> getAnimalsOlderThan(int age){
        List<Animal> result = animalRepository.findByAgeGreaterThan(age);
        if (result.size()==0){
            throw new ServiceException(String.format("No animals older than %2d", age ));
        }
        return result;
    }

    public Animal getOldestAnimal(){
        return animalRepository.findFirstByOrderByAgeDesc();
    }

    public Animal getAnimal(String name){
        Animal result =  animalRepository.findByName(name);
        if( result == null){
            throw new ServiceException(String.format("Animal with name %s does not exist", name));
        }
        return result;
    }
}
