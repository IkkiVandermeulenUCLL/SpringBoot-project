package be.ucll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "my_addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotNull(message = "Number is required")
    @Positive(message = "Number must be a positive integer")
    private int number;

    @NotBlank(message = "Place is required")
    private String place;

    @OneToOne(mappedBy = "address")
    private Stable stable;

    protected Address(){}
    public Address(Long id, String street, int number, String place){
        setId(id);
        setStreet(street);
        setNumber(number);
        setPlace(place);
    }
    public Address(String street, int number, String place){
        setStreet(street);
        setNumber(number);
        setPlace(place);
    }

    public Long getId() {
        return id;
    }
    public String getStreet() {
        return street;
    }
    public int getNumber() {
        return number;
    }
    public String getPlace() {
        return place;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setPlace(String place) {
        this.place = place;
    }
}
