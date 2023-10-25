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

import com.telemed.dao.AppointmentDaoImpl;
import com.telemed.dao.PatientDaoImpl;
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.medicalhistoryentity.Appointment;
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
	
	@Autowired
	private AppointmentDaoImpl appointmentDao;
	
	
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
	public ResponseEntity<String> register(@RequestBody Patient patient) {
		System.out.println("Storing patient into db");
		System.out.println(patient);
		
		//One exception may be patient already present with given email
		patientDao.store(patient);
		 
		return new ResponseEntity<>("sign-up successfull",HttpStatus.OK);
		  
	}
	
	// Patient login
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String,String> requestBody,
										HttpServletRequest request) {
		
		HttpSession session=request.getSession();
		if(session.isNew()) {
			System.out.println("\nPatient New Session");
		} else {
			System.out.println("\nPatient session already existed");
		}
		
		System.out.println("Patient to login");
		System.out.println("login detail: email = "+requestBody.get("email")+
							"\n               password = "+requestBody.get("password")+"\n");
		
		Patient patient=patientDao.extract(requestBody.get("email"));
		
		if(!requestBody.get("password").equals(patient.getPassword())) {
			System.out.println("Incorrect password");
			return new ResponseEntity<>("Incorrect password",HttpStatus.OK);
		}

		session.setAttribute("USER_EMAIL",patient.getEmail());
		return new ResponseEntity<>("login successful",HttpStatus.OK);
	}
	
	// View Profile
	@GetMapping("/view-profile")
	public ResponseEntity<Patient> viewProfile(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		
		if(session==null) {
			System.out.println("\nPatient is not logged-in Session= "+session);
			return new ResponseEntity<>(null,HttpStatus.OK);
		} else	System.out.println("Patient session already existed");
		
		String email=(String) session.getAttribute("USER_EMAIL");
		Patient patient=patientDao.extract(email);
		System.out.println("Viewing patient profile: "+email);
		System.out.println("View Profile: " + patient);
		return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
	@GetMapping("/medical-history")
	public ResponseEntity<List<Appointment>> getAppointments (HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		Patient patient=patientDao.extract((String)session.getAttribute("USER_EMAIL"));
		int patientId=patient.getId();
		List<Appointment> appointments=appointmentDao.extractAll(patientId);
		return new ResponseEntity<>(appointments,HttpStatus.OK);
	}
	
	// Logout API
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		System.out.println("Patient logout");
		HttpSession session = request.getSession(false); // Get the current session without creating a new one
		if (session != null) {
			session.invalidate(); // Invalidate the user's session
		}
		return new ResponseEntity<String>("logout successfull",HttpStatus.OK);
	}
	
	
	// check user exist
	@PostMapping("/emailexist")
	public ResponseEntity<String> checkEmailExists(@RequestBody Map<String,String> request) {
		System.out.println("Checking patient exist in DB : "+request.get("email"));
		patientDao.extract(request.get("email"));
		return new ResponseEntity<String>("email exist",HttpStatus.OK);
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
