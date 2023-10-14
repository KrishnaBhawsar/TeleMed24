package com.telemed.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telemed.dao.DoctorDaoImpl;
import com.telemed.userentities.Doctor;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorDaoImpl doctorDao;	

//	public ResponseEntity<Doctor> register(@RequestParam("name") String name,
//										   @RequestParam("email") String email,
//										   @RequestParam(name="phoneNo",required=false)  String phoneNo,
//										   @RequestParam("city") String city,
//										   @RequestParam("password") String password,
//										   @RequestParam("specialization") String specialization,
//										   @RequestParam("certificateNo") String certificateNo) {
//		
	
	
	
	
	
	
	
//	public ResponseEntity<Doctor> register(@ModelAttribute Doctor doctor) {
//		
//		System.out.println(doctor);
//		
//		doctorDaoImpl.store(doctor);
//		return new ResponseEntity<>(doctor,HttpStatus.OK);
//	}
	
	
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
