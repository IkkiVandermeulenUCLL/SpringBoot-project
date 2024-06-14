package be.ucll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Toy;
import be.ucll.repository.ToyRepository;

@Service
public class ToyService {
    
    @Autowired
    private ToyRepository toyRepository;
    private AnimalService animalService;

    public ToyService(ToyRepository toyRepository, AnimalService animalService){
        this.toyRepository = toyRepository;
        this.animalService = animalService;
    }

    public List<Toy> getToys(){
        return toyRepository.findAll();
    }

    public List<Toy> getToysByName(String name){
        return toyRepository.findByNameLike(name);
    }

    public Toy addToy(String name){
        return toyRepository.save(new Toy(name));
    }

    public void addAnimal(String name, String animalName){
        Toy toy = toyRepository.save(new Toy(name));
        animalService.addToy(animalName, toy);
    }
}
