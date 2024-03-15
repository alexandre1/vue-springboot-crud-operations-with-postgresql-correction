package com.yt.backend.repository;

import com.yt.backend.model.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
@Repository
public interface PageablePatientRepository extends PagingAndSortingRepository<Patient, Long> {
    public Page<Patient> findAll(Pageable pageable);

}
