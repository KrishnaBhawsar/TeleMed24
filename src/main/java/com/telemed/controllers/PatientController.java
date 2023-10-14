package com.telemed.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telemed.dao.PatientDaoImpl;
import com.telemed.emailservices.SendMailService;
import com.telemed.userentities.Patient;

@RestController
@RequestMapping("/patient")
@CrossOrigin
public class PatientController {
	
	@Autowired
	private PatientDaoImpl patientDao;
	
	@Autowired
	private SendMailService mailService;
	
	
	
	// sign-up of patient
	@PostMapping ("/register")
	public ResponseEntity<String> register(@RequestBody Patient patient) {
		
		 System.out.println(patient);
		 patientDao.store(patient);
		 
		 System.out.println("Sending email to "+patient.getEmail());
		 String otp=mailService.sendOtp(patient.getEmail());
		 System.out.println(otp);
		 return new ResponseEntity<>(otp,HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Patient>> getAll() {
		List<Patient> patients=patientDao.extractAll();
		return new ResponseEntity<List<Patient>>(patients,HttpStatus.OK);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Optional<Patient>> getById(@PathVariable("id") int primaryKey) {
		
		Optional<Patient> patient=patientDao.extract(primaryKey);
		
		return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
}
