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

import com.telemed.dao.DoctorDaoImpl;
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.userentities.Doctor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorDaoImpl doctorDao;	
	
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
	
	
	@PostMapping("/register")
	public ResponseEntity<Doctor> register(@RequestBody Doctor doctor) {
		System.out.println("Storing patient into db");
		System.out.println(doctor);
		
		//One exception may be patient already present with given email
		doctorDao.store(doctor); 
		return new ResponseEntity<>(doctor,HttpStatus.OK);
	}
	
	
	// Doctor login
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String,String> requestBody,
										HttpServletRequest request) {
		
		HttpSession session=request.getSession();
		if(session.isNew()) {
			System.out.println("Doctor New Session");
		} else {
			System.out.println("Doctor session already existed");
		}
		
		System.out.println("Doctor to login");
		System.out.println("login detail : email = "+requestBody.get("email")+
							"\n               password = "+requestBody.get("password"));
		
		Doctor doctor=doctorDao.extract(requestBody.get("email"));
		
		if(!requestBody.get("password").equals(doctor.getPassword())) {
			System.out.println("Incorrect password");
			return new ResponseEntity<>("Incorrect password",HttpStatus.OK);
		}

		session.setAttribute("USER_EMAIL",doctor.getEmail());
		return new ResponseEntity<>("login successful",HttpStatus.OK);
	}
	
	@GetMapping("/view-profile")
	public ResponseEntity<Doctor> viewProfile(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		
		if(session==null) {
			System.out.println("\nPatient is not logged-in Session= "+session);
			return new ResponseEntity<>(null,HttpStatus.OK);
		} else	System.out.println("Patient session already existed");
		
		String email=(String) session.getAttribute("USER_EMAIL");
		Doctor doctor=doctorDao.extract(email);
		System.out.println("Viewing doctor profile: "+email);
		System.out.println("View Profile: " + doctor);
		return new ResponseEntity<>(doctor,HttpStatus.OK);
	}
	
	@PostMapping("/emailexist")
	public ResponseEntity<String> checkEmailExists(@RequestBody Map<String,String> request) {
		System.out.println("Checking patient exist in DB : "+request.get("email"));
		doctorDao.extract(request.get("email"));
		return new ResponseEntity<String>("email exist",HttpStatus.OK);
	}	
	
	
	
	
	
	
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Optional<Doctor>> getById(@PathVariable("id") int id) {
		
		System.out.println(id);
		Optional<Doctor> doctor=doctorDao.extract(id);
		 
		if(doctor.isPresent()) {
			return new ResponseEntity<>(doctor, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getbyname/{name}")
	public ResponseEntity<List<Doctor>> getByName(@PathVariable("name") String name) {
		
		System.out.println(name);
		List<Doctor> doctors=doctorDao.extractByName(name);
		  
		if(doctors.isEmpty()) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<>(doctors,HttpStatus.OK);
	}
	
	
}
