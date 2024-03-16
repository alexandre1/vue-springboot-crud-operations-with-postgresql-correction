
package com.yt.backend.controller;

import com.yt.backend.model.Patient;
import com.yt.backend.service.PatientService;
import com.yt.backend.service.MailWithAttachmentService;
import com.yt.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
            final String secretKey = "aslkdfjlkj)/d89723897bc";
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
    @CrossOrigin
    @RequestMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable("id") long id) {
        return patientService.getPatientById(id);
    }

    // getPatients
    @RequestMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @CrossOrigin
    @GetMapping("/entities")
    public Page<Patient> getEntities(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size, Pageable pageable) {
        Pageable pageableQuery = PageRequest.of(page, size);
        System.out.println("size : " + size);
        return patientService.getAllPatientsPaginated(pageableQuery);
    }

    // updatePatient
    @CrossOrigin
    @PutMapping("/patient/{id}")
    public Patient updatePatient(
            @PathVariable("id") long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("patient") String patientJson
    ) {
        try {
            Patient patient = new ObjectMapper().readValue(patientJson, Patient.class);
            patient.setId(id);
            patient.setPicture(file.getBytes());
            patientService.updatePatient(patient);
            return patient;
        } catch (IOException e) {
            // Handle exception appropriately
            return null;
        }
    }

    // deletePatientById
    @DeleteMapping("/patient/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        patientService.deletePatient(id);
        return "Patient Deleted";
    }

    @CrossOrigin
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

    @CrossOrigin
    @PutMapping("/forgetPassword/{email}")
    public Patient forgetPassword(
            @PathVariable("email") String email
    ) {
        MailWithAttachmentService mail = new MailWithAttachmentService();
        Patient patientRetrieved = patientService.getPatientByEmail(email);
        System.out.println(patientRetrieved.getPassword());
        if (patientRetrieved != null) {
            System.out.println("Patient Retrieved - ID: " + patientRetrieved.getId());
            try {
                mail.sendPassword(mail.getSession(), email, patientRetrieved);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (MessagingException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }


}
