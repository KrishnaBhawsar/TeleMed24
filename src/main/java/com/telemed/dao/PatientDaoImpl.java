package com.telemed.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.telemed.dao.rowmapper.PatientRowMapper;
import com.telemed.userentities.Patient;


@Repository
public class PatientDaoImpl implements PatientDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private PatientRowMapper patientRowMapper=PatientRowMapper.getRowMapper();
	
	// Method to store patient into DB
	public int store(Patient patient) {
		
		String storePatientQuery="INSERT INTO patient "
				+ "(name,email,phone_no,city,password)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		
		int rowsAffected=jdbcTemplate.update(storePatientQuery,
										patient.getName(),
										patient.getEmail(),
										patient.getPhoneNo(),
										patient.getCity(),
										patient.getPassword()
										);
		
		return rowsAffected;
	}
	
	
	// Method to extract whole Patient Object from DB  
	public Optional<Patient> extract(int primaryKey) {
		
		String extractPatientQuery="SELECT * FROM patient WHERE id=?";
		
		Patient patient=jdbcTemplate.queryForObject(extractPatientQuery, patientRowMapper, primaryKey);
		Optional<Patient> optionalPatient=Optional.of(patient);
		
		return optionalPatient;
	}
	
	
	
	// Method to extract whole patient by name Object from DB  
	public List<Patient> extractByName(String name) {
		
		String storePatientQuery="SELECT * FROM patient WHERE name=?";
		
		List<Patient> patientList=jdbcTemplate.query(storePatientQuery, patientRowMapper, name);
		return patientList;
	}
	
	
	
	// Method to extract whole Doctor by city Object from DB  
	public List<Patient> extractByCity(String city) {
		
		String extractPatientQueryByName="SELECT * FROM patient WHERE name=?";
		
		List<Patient> doctorList=jdbcTemplate.query(extractPatientQueryByName,patientRowMapper, city);
		return doctorList;
	}
	

	
	
	
	
	
	
	
	@Override
	public String extractName(int primaryKey) {
		String name=null;
		String extractNameQuery="SELECT name FROM patient WHERE id=?";
		name=(String)jdbcTemplate.queryForObject(extractNameQuery, 
										 String.class, 
										 primaryKey);
		return name;
	}

	@Override
	public String extractEmail(int primaryKey) {
		String email=null;
		String extractEmailQuery="SELECT email FROM patient WHERE id=?";
		email=(String)jdbcTemplate.queryForObject(extractEmailQuery, 
										 String.class, 
										 primaryKey);
		return email;
	}

	@Override
	public String extractPhoneNo(int primaryKey) {
		String phoneNo=null;
		String extractPhnoeNoQuery="SELECT phone_no FROM patient WHERE id=?";
		phoneNo=(String)jdbcTemplate.queryForObject(extractPhnoeNoQuery, 
										 String.class, 
										 primaryKey);
		return phoneNo;
	}

	@Override
	public String extractCity(int primaryKey) {
		String city=null;
		String extractCityQuery="SELECT city FROM patient WHERE id=?";
		city=(String)jdbcTemplate.queryForObject(extractCityQuery, 
										 String.class, 
										 primaryKey);
		return city;
	}

	@Override
	public String extractPassword(int primaryKey) {
		String password=null;
		String extractPasswordQuery="SELECT password FROM patient WHERE id=?";
		password=(String)jdbcTemplate.queryForObject(extractPasswordQuery, 
										 String.class, 
										 primaryKey);
		return password;
	}

	@Override
	public Date extractDob(int primaryKey) {
		Date dobDate=null;
		String extractDateQuery="SELECT dob FROM patient WHERE id=?";
		dobDate=(Date)jdbcTemplate.queryForObject(extractDateQuery, 
										 Date.class, 
										 primaryKey);
		return dobDate;
	}

}
