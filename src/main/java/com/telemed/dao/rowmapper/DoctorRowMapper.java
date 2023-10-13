package com.telemed.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.telemed.userentities.Doctor;

public class DoctorRowMapper implements RowMapper<Doctor>{
	
	@Override
	public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
		Doctor doctor=new Doctor();
		
		doctor.setId(rs.getInt("id"));
		doctor.setName(rs.getString("name"));
		doctor.setEmail(rs.getString("email"));
		doctor.setPhoneNo(rs.getString("phone_no"));
		doctor.setCity(rs.getString("city"));
		doctor.setCertificateNo(rs.getString("ccertificate_no"));
		doctor.setRating(rs.getInt("rating"));
		doctor.setSpecialization(rs.getString("specialization"));
		doctor.setPassword(rs.getString("password"));
		
		return doctor;
	}
}
