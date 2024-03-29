package com.yt.backend.service;

import com.yt.backend.model.Patient;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public interface PatientService {
    public Patient addPatient(Patient patient);
    public Patient getPatientById(long id);
    public Patient getPatientByEmail(String email);
    public List<Patient> getPatients();
    public void deletePatient(long id);
    public Patient updatePatient(Patient patient);
    public Page<Patient> getAllPatientsPaginated(Pageable pageable);

}
