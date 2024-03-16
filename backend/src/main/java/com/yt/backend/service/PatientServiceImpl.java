package com.yt.backend.service;

import com.yt.backend.model.Patient;
import com.yt.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    // repository
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientEmailRepository patientEmailRepository;

    @Autowired
    private PageablePatientRepository pageablePatientepository;

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(long id) {

        return patientRepository.findById(id).get();
    }
    @Transactional(readOnly = true)
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    @Override
    public List<Patient> getPatients() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Page<Patient> getAllPatientsPaginated(Pageable pageable) {
        return pageablePatientepository.findAll(pageable);
    }

}
