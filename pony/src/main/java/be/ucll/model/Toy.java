package be.ucll.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "my_toys")
public class Toy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @ManyToMany(mappedBy = "toys")
    @JsonIgnore
    private List<Animal> animals = new ArrayList<>();

    protected Toy(){}

    public Toy(String name){
        setName(name);
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Animal> getAnimals() {
        return animals;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
