package be.ucll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Toy;

@Repository
public interface ToyRepository extends JpaRepository<Toy, Long> {
    public List<Toy> findByNameLike(String name);
}
