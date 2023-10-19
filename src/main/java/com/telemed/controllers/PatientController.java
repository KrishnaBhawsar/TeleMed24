package com.telemed.controllers;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telemed.dao.PatientDaoImpl;
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.userentities.Patient;

import jakarta.servlet.http.Cookie;

@RestController
@CrossOrigin
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientDaoImpl patientDao;
	
	@Autowired
	private EmailServiceImpl mailService;
	
	
	// OTP request
	@PostMapping("/reqOTP")
	public ResponseEntity<String> requestOtp(@RequestBody Map<String,String> requestBody)
	{
		String to=requestBody.get("email");
		System.out.println("Sending otp to "+to);
		String otp=mailService.sendOtp(to);
		System.out.println("otp = "+otp);  // working 
		
		return new ResponseEntity<String>(otp,HttpStatus.OK);
	}
	
	
	// sign-up of patient
	@PostMapping ("/register")
	public ResponseEntity<Patient> register(@RequestBody Patient patient) {
		
		// One exception may be patient already present with given email
		 System.out.println(patient);
		 System.out.println("Storing patient into db");
		 patientDao.store(patient);
		 
		 ResponseCookie cookie=ResponseCookie.from("login-status","true").build();
		 return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(patient); 
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String,String> requestBody) {
		System.out.println("Trying to login");
		System.out.println("login detail : email = "+requestBody.get("email")+"\n               password = "+requestBody.get("password"));
		
		Patient patient=patientDao.extract(requestBody.get("email"));
		
		if(patient==null) {
			System.out.println("Patient not exist with email-id "+requestBody.get("email"));
			return new ResponseEntity<>("Patient not found with email",
										HttpStatus.NOT_FOUND);
		} else if(!requestBody.get("password").equals(patient.getPassword())) {
			System.out.println("Incorrect password");
			return new ResponseEntity<>("Incorrect password",	
										HttpStatus.OK);
		}
		
		Cookie cookie=new Cookie("patient-id",patient.getId()+"");
		HttpHeaders headers=new HttpHeaders();
		headers.add("Cookie", cookie.toString());
		return new ResponseEntity<> ("login successfull",headers,HttpStatus.OK);
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
