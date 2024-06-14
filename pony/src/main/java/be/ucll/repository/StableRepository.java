package be.ucll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.model.Stable;

@Repository
public interface StableRepository extends JpaRepository<Stable, Long>{
    public boolean existsByAddressId(Long addressId);
}
