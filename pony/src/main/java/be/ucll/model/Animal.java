package be.ucll.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "my_animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Age cannot be smaller than 1")
    @Max(value = 50, message = "Age cannot be greater than 50")
    private int age;

    @ManyToOne
    @JsonBackReference
    private Stable stable;

    @ManyToMany
    @JoinTable(
        name = "animals_toys",
        joinColumns = @JoinColumn(name = "animal_id"),
        inverseJoinColumns = @JoinColumn(name = "toy_id")    
    )
    private List<Toy> toys = new ArrayList<>();

    @OneToMany(mappedBy = "animal")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    protected Animal(){}

    public Animal(String name, int age){
        setName(name);
        setAge(age);
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setStable(Stable stable) {
        this.stable = stable;
    }    
    public void setToys(List<Toy> toys) {
        this.toys = toys;
    }
    public void setMedicalRecords(List<MedicalRecord> medicalRecords){
        this.medicalRecords = medicalRecords;
    }

    public long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public Stable getStable(){
        return this.stable;
    }
    public List<Toy> getToys() {
        return this.toys;
    }
    public List<MedicalRecord> getMedicalRecords(){
        return this.medicalRecords;
    }
    
    public void addToy(Toy toy){
        this.toys.add(toy);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord){
        this.medicalRecords.add(medicalRecord);
        medicalRecord.setAnimal(this);
    }
}
