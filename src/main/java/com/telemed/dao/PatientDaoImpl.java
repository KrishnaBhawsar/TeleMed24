package com.telemed.dao;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PatientDaoImpl implements PatientDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

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
