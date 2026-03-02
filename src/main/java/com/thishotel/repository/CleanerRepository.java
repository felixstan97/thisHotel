package com.thishotel.repository;

import com.thishotel.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
    Optional<Cleaner> findByEmail(String email);
}
