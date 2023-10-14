package com.telemed.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PatientController {
	
	@Autowired
	private PatientDaoImpl patientDao;
	
	@Autowired
	private SendMailService mailService;
	
	@PostMapping ("/register")
	public ResponseEntity<Patient> register(@RequestBody Patient patient) {
		 patientDao.store(patient);
		 System.out.println(patient);
		 
		 System.out.println("Sending email to "+patient.getEmail());
		 System.out.println(mailService.sendOtp(patient.getEmail()));
		 
		 return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Optional<Patient>> getById(@PathVariable("id") int primaryKey) {
		
		Optional<Patient> patient=patientDao.extract(primaryKey);
		
		return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
}
