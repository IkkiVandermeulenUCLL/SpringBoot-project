package be.ucll.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Animal;
import be.ucll.model.MedicalRecord;
import be.ucll.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    private AnimalService animalService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, AnimalService animalService){
        this.medicalRecordRepository = medicalRecordRepository;
        this.animalService = animalService;
    }

    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord addMedicalRecord(LocalDate registerationDate, String description){
        return medicalRecordRepository.save(new MedicalRecord(registerationDate, description));
    }

    public Animal addMedicalrecordToAnimal(String animalName, MedicalRecord medicalRecord){
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        return animalService.addMedicalRecord(animalName, medicalRecord);
    }

    public MedicalRecord closeMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> getMedicalRecordsOfAnimalAfter(String animalName, LocalDate date) {
        Animal animal = animalService.getAnimal(animalName);
        return medicalRecordRepository.findByAnimalIdAndRegistrationDateAfter(animal.getId(), date);
    }
}
