package com.telemed.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telemed.dao.PatientDaoImpl;
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.userentities.Patient;

@RestController
@CrossOrigin
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientDaoImpl patientDao;
	
	@Autowired
	private EmailServiceImpl mailService;
	
	
	// OTP request
	@GetMapping("/reqOTP/{to}")
	public ResponseEntity<String> requestOtp(@PathVariable("to") String to)
	{
		String otp=mailService.sendOtp(to);
		System.out.println("Sending otp to "+to);
		System.out.println("otp is "+otp);
		
		return new ResponseEntity<String>(otp,HttpStatus.OK);
	}
	
	
	
	@PostMapping ("/register")
	public ResponseEntity<String> register() {
		
		// One exception may be patient already present with given email
//		 System.out.println(patient);
//		 System.out.println("Storing patient into db");
//		 patientDao.store(patient);
		 
		 ResponseCookie cookie=ResponseCookie.from("user-name","xyz").build();
		 return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body("hello"); 
	}
	
	
	
	
	
	
	// sign-up of patient
//	@PostMapping ("/register")
//	public ResponseEntity<Patient> register(@RequestBody Patient patient) {
//		
//		// One exception may be patient already present with given email
//		 System.out.println(patient);
//		 System.out.println("Storing patient into db");
//		 patientDao.store(patient);
//		 
//		 ResponseCookie cookie=ResponseCookie.from("user-name","xyz").build();
//		 return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(patient); 
//	}
	
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
