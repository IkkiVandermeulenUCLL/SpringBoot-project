package be.ucll.repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ucll.model.Address;
import be.ucll.model.Animal;
import be.ucll.model.MedicalRecord;
import be.ucll.model.Stable;
import be.ucll.model.Toy;
import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {
    @Autowired
    private AnimalRepository animalRepository;
    private StableRepository stableRepository;
    private AddressRepository addressRepository;
    private ToyRepository toyRepository;
    private MedicalRecordRepository medicalRecordRepository;

    public DbInitializer(AnimalRepository animalRepository, StableRepository stableRepository, AddressRepository addressRepository, ToyRepository toyRepository, MedicalRecordRepository medicalRecordRepository){
        this.animalRepository = animalRepository;
        this.stableRepository = stableRepository;
        this.addressRepository = addressRepository;
        this.toyRepository = toyRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @PostConstruct
    public void initialize(){
        
        MedicalRecord medicalRecord1 = new MedicalRecord(LocalDate.of(2024, 05,01), "Chicken has eaten too many easter eggs");
        MedicalRecord medicalRecord2 = new MedicalRecord(LocalDate.of(2024, 06, 10), "Chickenpox");
        
        Stable stblHn = new Stable("StblHn",5);
        Stable ponyCo = new Stable("PonyCo",3);
        Stable kasteelHoeve = new Stable("KasteelHoeve", 10);

        Animal bella = animalRepository.save(new Animal("Bella", 20));
        Animal luna = animalRepository.save(new Animal("Luna", 10));
        Animal muriel = animalRepository.save(new Animal("Muriel", 2));
        Animal little = animalRepository.save(new Animal("Little", 1));

        Toy bone = toyRepository.save(new Toy("Bone"));
        Toy ball = toyRepository.save(new Toy("Ball"));

        stblHn.addAnimal(luna);
        ponyCo.addAnimal(muriel);
        
        bella.addToy(ball);
        little.addToy(bone);

        luna.addMedicalRecord(medicalRecord1);
        muriel.addMedicalRecord(medicalRecord2);

        animalRepository.save(bella);
        animalRepository.save(little);

        medicalRecordRepository.save(medicalRecord1);
        medicalRecordRepository.save(medicalRecord2);

        Address galgenstraat = addressRepository.save(new Address("Naamsestraat", 20, "Leuven"));
        Address naamsestraat = addressRepository.save(new Address("Galgenstraat", 19, "Orsmaal"));
        Address janseniusstraat = addressRepository.save(new Address("Janseniusstraat", 39, "Leuven"));

        stblHn.setAddress(galgenstraat);
        ponyCo.setAddress(naamsestraat);
        
        stableRepository.save(kasteelHoeve);
        stableRepository.save(stblHn);
        stableRepository.save(ponyCo);
    }
}
