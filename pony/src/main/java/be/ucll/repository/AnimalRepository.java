package be.ucll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
    public boolean existsByName(String name);

    public Animal findByName(String name);

    public List<Animal> findByAgeGreaterThan(int age);

    public Animal findFirstByOrderByAgeDesc();
}
