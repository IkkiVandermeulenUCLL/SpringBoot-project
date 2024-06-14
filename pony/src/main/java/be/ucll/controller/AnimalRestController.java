package be.ucll.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.ucll.model.Animal;
import be.ucll.model.DomainException;
import be.ucll.model.MedicalRecord;
import be.ucll.model.Stable;
import be.ucll.service.AnimalService;
import be.ucll.service.MedicalRecordService;
import be.ucll.service.ServiceException;
import be.ucll.service.StableService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;



@RestController
@RequestMapping("animals")
public class AnimalRestController {
    @Autowired
    private AnimalService animalService;
    private StableService stableService;
    private MedicalRecordService medicalRecordService;
    
    public AnimalRestController(AnimalService animalService, StableService stableService, MedicalRecordService medicalRecordService){
        this.animalService = animalService;
        this.stableService = stableService;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<Animal> getAnimals() {
        return animalService.getAnimals();
    }

    @GetMapping("/age/{age}")
    public List<Animal> getAnimalsOlderThan(@PathVariable int age) {
        return animalService.getAnimalsOlderThan(age);
    }

    @GetMapping("/oldest")
    public Animal getOldestAnimal() {
        return animalService.getOldestAnimal();
    }

    @GetMapping("/stables")
    public List<Stable> getAllStables() {
        return stableService.getAllStables();
    }

    @GetMapping("/{animalName}/stable")
    public Stable getStableOfAnimal(@PathVariable String animalName) {
        return stableService.getStableOfAnimal(animalName);
    }
    
    @GetMapping("/openmedicalrecords")
    public List<Animal> getAnimalsWithOpenMedicalRecords() {
        return animalService.getAnimalsWithOpenMedicalRecords();
    }
    
    @GetMapping("/medicalrecords/{animalName}/{date}")
    public List<MedicalRecord> getMedicalRecordsOfAnimalAfter(@PathVariable String animalName, @PathVariable LocalDate date) {
        return medicalRecordService.getMedicalRecordsOfAnimalAfter(animalName, date);
    }
    

    @PostMapping("/{animalName}/stable")
    public Stable AddAnimalToStable(@PathVariable String animalName,@RequestBody(required = false) Stable stable,@RequestParam(required = false) Optional<Long> stableId) {
        if (stable!=null){
            return stableService.addStable(animalName, stable);
        }
        return stableService.addAnimal(animalName, stableId);
    }
    
    @PostMapping
    public Animal addAnimal(@RequestBody Animal animal) {
        return animalService.addAnimal(animal);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Map<String,String> handleValidationException(ConstraintViolationException e){
        Map<String,String> response = new HashMap<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> error : violations){
            response.put("ValidationException", error.getMessageTemplate());
        }
        return response;
    }    

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DomainException.class})
    public Map<String, String> handleDomainException(DomainException e){
        Map<String, String> response = new HashMap<>();
        response.put("DomainException", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HandlerMethodValidationException.class})
    public Map<String, String> handleMethodValidationException(HandlerMethodValidationException e){
        Map<String, String> response = new HashMap<>();
        response.put("MethodValidationException", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public Map<String, String> handleServiceException(ServiceException e){
        Map<String, String> response = new HashMap<>();
        response.put("ServiceException", e.getMessage());
        return response;
    }
}
