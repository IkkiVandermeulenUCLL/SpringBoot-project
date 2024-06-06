package be.ucll.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ucll.model.Animal;
import be.ucll.model.Stable;
import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
    private AnimalRepository animalRepository;
    private StableRepository stableRepository;

    @Autowired
    public DbInitializer(AnimalRepository animalRepository, StableRepository stableRepository){
        this.animalRepository = animalRepository;
        this.stableRepository = stableRepository;
    }

    @PostConstruct
    public void initialize(){
        Animal Bella = new Animal("Bella", 20);
        Animal Luna = new Animal("Luna", 10);
        Animal Muriel = new Animal("Muriel", 2);
        Animal Little = new Animal("Little", 1);

        Stable StblHn = new Stable("StblHn",5);
        Stable PonyCo = new Stable("PonyCo",3);

        StblHn.addAnimal(Luna);
        PonyCo.addAnimal(Muriel);

        animalRepository.save(Bella);
        animalRepository.save(Luna);
        animalRepository.save(Muriel);
        animalRepository.save(Little);

        stableRepository.save(StblHn);
        stableRepository.save(PonyCo);
    }
}
