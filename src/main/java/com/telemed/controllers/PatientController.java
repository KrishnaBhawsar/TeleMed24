package com.telemed.controllers;

import java.util.List;
import java.util.Map;
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
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.userentities.Patient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public ResponseEntity<Patient> register(@RequestBody Patient patient,HttpSession session) {
		System.out.println("Storing patient into db");
		System.out.println(patient);
		
//		 One exception may be patient already present with given email
		 patientDao.store(patient);
		 
		 return new ResponseEntity<>(patient,HttpStatus.OK);
		  
	}
	
	// Patient login
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String,String> requestBody,
										HttpServletRequest request) {
		
		HttpSession session=request.getSession();
		if(session.isNew()) {
			System.out.println("New Session");
		} else {
			System.out.println("User session already existed");
		}
		
		System.out.println("Trying to login");
		System.out.println("login detail : email = "+requestBody.get("email")+
							"\n               password = "+requestBody.get("password")+"\n");
		
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

		session.setAttribute("USER_EMAIL",patient.getEmail());
		return new ResponseEntity<>("login successful",HttpStatus.OK);
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
