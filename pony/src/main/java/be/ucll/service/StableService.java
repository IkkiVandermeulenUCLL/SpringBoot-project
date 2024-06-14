package be.ucll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Address;
import be.ucll.model.Animal;
import be.ucll.model.Stable;
import be.ucll.repository.StableRepository;

@Service
public class StableService {
    @Autowired
    private StableRepository stableRepository;
    private AnimalService animalService;
    private AddressService addressService;

    public StableService(StableRepository stableRepository, AnimalService animalService, AddressService addressService){
        this.stableRepository = stableRepository;
        this.animalService = animalService;
        this.addressService = addressService;
    }

    public List<Stable> getAllStables(){
        return stableRepository.findAll();
    }

    public Stable addStable(String animalName, Stable stable){
        Animal animal = animalService.getAnimal(animalName);
        Stable result = stableRepository.save(stable);
        result.addAnimal(animal);
        animal.setStable(stable);
        result = stableRepository.save(result);
        return result;
    }

    public Stable addAnimal(String animalName, Optional<Long> stableId){
        Animal animal = animalService.getAnimal(animalName);
        Long id = stableId.get();
        if (stableRepository.findById(id).isEmpty()){
            throw new ServiceException(String.format("Stable with id %s does not exist", id));
        }
        Stable stable = stableRepository.findById(id).get();
        animal.setStable(stable);
        stable.addAnimal(animal);
        return stableRepository.save(stable);
    }

    public Stable getStableOfAnimal(String animalName){
        Animal animal = animalService.getAnimal(animalName);
        if (animal.getStable()==null){
            throw new ServiceException("Animal has no stable");
        }
        return animal.getStable();
    }

    public Stable addStableAndAddress(Stable stable){
        Address address = new Address(stable.getAddress().getStreet(), stable.getAddress().getNumber(), stable.getAddress().getPlace());
        address = addressService.addAddress(address);
        stable.setAddress(address);
        return stableRepository.save(stable);
    }

    public Stable addAddressToStable(Long addressId, Long stableId){
        if (stableRepository.existsByAddressId(addressId)){
            throw new ServiceException(String.format("Addres with id %s is already associated with a stable",addressId));
        }
        Address address = addressService.getAddress(addressId);
        if (address == null){
            throw new ServiceException(String.format("Address with id %s does not exist", addressId));
        }
        if (stableRepository.findById(stableId).isEmpty()){
            throw new ServiceException(String.format("Stable with id %s does not exist", stableId));
        }
        Stable stable = stableRepository.findById(stableId).get();
        if (stable.getAddress()!=null){
            throw new ServiceException(String.format("Stable %s already has an address on %s", stable.getName(), stable.getAddress().getStreet()));
        }
        stable.setAddress(address);
        return stableRepository.save(stable);
    }
}
