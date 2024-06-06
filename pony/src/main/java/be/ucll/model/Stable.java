package be.ucll.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "my_stables")
public class Stable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Capacity must be a positive integer")
    private int capacity;

    @OneToMany
    @JoinColumn(name = "stable_id")
    @JsonBackReference
    private List<Animal> animals;

    protected Stable(){ this.animals = new ArrayList<>();}

    public Stable(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.animals = new ArrayList<Animal>();
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public void setAnimals(List<Animal> animals) {
        this.animals = new ArrayList<Animal>();
    }
    
    public long getId() {
        return this.id;
    }    
    public String getName() {
        return this.name;
    }
    public List<Animal> getAnimals() {
        return this.animals;
    }
    public int getCapacity() {
        return this.capacity;
    }

    public void addAnimal(Animal animal){
        if (this.capacity>0){
            this.animals.add(animal);
            this.capacity -= 1;
        }else{
            throw new DomainException("Not enough space");
        }
        
    }
}
