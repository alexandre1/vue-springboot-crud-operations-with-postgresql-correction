package com.yt.backend.repository;

import com.yt.backend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientEmailRepository extends JpaRepository<Patient, Long> {
    // You can add custom query methods here if needed
    // For example:
     public Patient findByEmail(String email);
}