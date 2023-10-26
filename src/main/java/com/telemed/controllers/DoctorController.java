package com.telemed.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telemed.dao.AppointmentTimeSlotDaoImpl;
import com.telemed.dao.DoctorDaoImpl;
import com.telemed.emailservices.EmailServiceImpl;
import com.telemed.exceptions.UserWithEmailAlreadyExistException;
import com.telemed.medicalhistoryentity.AppointmentTimeSlot;
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
	
	@Autowired
	private AppointmentTimeSlotDaoImpl timeSlotDao;
	

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
	
	@GetMapping("/getalldoctors")
	public ResponseEntity<List<Doctor>> getAllDoctors(HttpServletRequest request) {
		List<Doctor> doctors=doctorDao.extractAll();
		return new ResponseEntity<List<Doctor>>(doctors,HttpStatus.OK);
	}
	
	
	@GetMapping("/getdoctorby")
	public ResponseEntity<List<Doctor>> getDoctorBy(@RequestParam("searchBy") String searchBy,@RequestParam("value") String value) {
		List<Doctor> doctors=null;
		System.out.println("\nSearching doctor by "+searchBy+"\n value = "+value);
		
		if(searchBy.equals("name")) {
			doctors=doctorDao.extractByName(value+"%");
		} else if(searchBy.equals("city")) {
			doctors=doctorDao.extractByCity(value);
		} else {
			doctors=doctorDao.extractBySpecialization(value);
		}
		
		System.out.println(doctors);
		return new ResponseEntity<List<Doctor>>(doctors,HttpStatus.OK);
	}
	
	// Get all available Time slots of a particular doctor
	@GetMapping("/getavailableslots")
	public ResponseEntity<List<AppointmentTimeSlot>> getAvailableSlots(@RequestParam("doctorId") int doctorId) {
		List<AppointmentTimeSlot> slots=null;
		System.out.println("\nSearching available slots of doctor :"+doctorId);
		
		slots=timeSlotDao.extractAvailable(doctorId);
		return new ResponseEntity<List<AppointmentTimeSlot>>(slots,HttpStatus.OK);
	}
	
	// Booking slot
	@PutMapping("/bookslot")
	public ResponseEntity<String> bookSlot(@RequestBody Map<String,String> request) {
		int slotId=Integer.parseInt((String)request.get("slotId"));
		timeSlotDao.updateSlot(slotId);
		
		return new ResponseEntity<String>("slot booked",HttpStatus.OK);
	}
	
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
	@PostMapping("/emailexist")
	public ResponseEntity<String> checkEmailExists(@RequestBody Map<String,String> doctor) {
		System.out.println("Checking doctor exist in DB : "+doctor.get("email"));
		doctorDao.extract(doctor.get("email"));
		System.out.println("dfgegwerg");
		throw new UserWithEmailAlreadyExistException();
	}
}
