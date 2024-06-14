package be.ucll.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "my_medicalrecords")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Registration date is required")
    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDate registrationDate;

    private LocalDate closingDate;

    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference
    private Animal animal;

    protected MedicalRecord(){}

    public MedicalRecord(LocalDate registrationDate, String description){
        setRegistrationDate(registrationDate);
        setDescription(description);
    }

    public Long getId() {
        return this.id;
    }
    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }
    public LocalDate getClosingDate() {
        return this.closingDate;
    }
    public String getDescription() {
        return this.description;
    }
    public Animal getAnimal(){
        return this.animal;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    public void setClosingDate(LocalDate closingDate) {
        if (this.registrationDate.plusDays(1).isAfter(closingDate)){
            throw new DomainException("Closing date must be atleast one day after the registration date");
        }
        this.closingDate = closingDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAnimal(Animal animal){
        this.animal = animal;
    }
}
