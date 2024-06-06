package be.ucll.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    Stable stable;
    
    protected Animal(){}
    public Animal(String name, int age){
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setStable(Stable stable) {
        this.stable = stable;
    }

    public long getId(){
        return this.id;
    }
    public String getname(){
        return this.name;
    }
    public int getage(){
        return this.age;
    }
    public Stable getStable(){
        return this.stable;
    }
}
