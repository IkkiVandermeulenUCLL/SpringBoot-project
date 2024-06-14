package be.ucll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.model.Animal;
import be.ucll.model.MedicalRecord;
import be.ucll.service.MedicalRecordService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordRestController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    public MedicalRecordRestController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/{animalName}")
    public Animal addMedicalrecordToAnimal(@PathVariable String animalName, @RequestBody @Valid MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalrecordToAnimal(animalName, medicalRecord);
    }

    @PutMapping()
    public MedicalRecord putMethodName(@RequestBody @Valid MedicalRecord medicalRecord) {
        return medicalRecordService.closeMedicalRecord(medicalRecord);
    }    
}
