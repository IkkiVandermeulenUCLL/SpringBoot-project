package be.ucll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Animal;
import be.ucll.model.Stable;
import be.ucll.repository.StableRepository;

@Service
public class StableService {
    private StableRepository stableRepository;

    @Autowired
    public StableService(StableRepository stableRepository){
        this.stableRepository = stableRepository;
    }

    public Stable addStable(Animal animal, Stable stable){
        Stable result = stableRepository.save(stable);
        result.addAnimal(animal);
        result = stableRepository.save(result);
        return result;
    }

    public Stable addAnimal(Animal animal, Optional<Long> stableId){
        Stable stable = stableRepository.findById(stableId.get()).get();
        if (stable == null){
            throw new ServiceException(String.format("Stable with id %s does not exist", stableId));
        }
        stable.addAnimal(animal);
        return stableRepository.save(stable);
    }
}
