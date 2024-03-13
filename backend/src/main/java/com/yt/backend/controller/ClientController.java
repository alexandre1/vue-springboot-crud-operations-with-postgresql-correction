package com.yt.backend.controller;

import com.yt.backend.model.Patient;
import com.yt.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    // service
    @Autowired
    private PatientService patientService;

    @RequestMapping("/")
    public String hello_world() {
        return "Hello World";
    }

    // add Patient
    @PostMapping("/add")
    public String addPatient(
            @RequestParam("file") MultipartFile file,
            @RequestParam("patient") String patientJson
    ) {
        try {
            Patient patient = new ObjectMapper().readValue(patientJson, Patient.class);
            patient.setPicture(file.getBytes());
            patientService.addPatient(patient);
            return "Patient Added Successfully.";
        } catch (IOException e) {
            // Handle exception appropriately
            return "Failed to add patient: " + e.getMessage();
        }
    }

    // getPatientById
    @RequestMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable("id") long id) {
        return patientService.getPatientById(id);
    }

    // getPatients
    @RequestMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    // updatePatient
    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient) {
        return patientService.updatePatient(patient);
    }

    // deletePatientById
    @DeleteMapping("/patient/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        patientService.deletePatient(id);
        return "Patient Deleted";
    }

    @GetMapping("/patients/{id}/picture")
    public ResponseEntity<byte[]> getPatientPicture(@PathVariable("id") long id) {
        try {
            // Retrieve the patient from the service
            Patient patient = patientService.getPatientById(id);
            byte[] picture = patient.getPicture();

            if (picture != null) {
                // If the patient has a picture, return it with appropriate headers
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(picture);
            } else {
                // If the patient does not have a picture, return 404 not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}