package com.telemed.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.telemed.medicalhistoryentity.Appointment;

public class AppointmentRowMapper implements RowMapper<Appointment>{
	
	static private AppointmentRowMapper appointmentRowMapper;

	@Override
	public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Appointment appointment=new Appointment();
		
		appointment.setId(rs.getInt("id"));
		appointment.setPatientId(rs.getInt("patient_id"));
		appointment.setDoctorId(rs.getInt("doctor_id"));
		appointment.setDate(rs.getDate("appointment_date"));
		return appointment;
	}
	
	// this constructor make if singleton
	private AppointmentRowMapper() {
		super();
	}
	
	public static AppointmentRowMapper getRowMapper() {
		if(appointmentRowMapper==null) {
			appointmentRowMapper=new AppointmentRowMapper();
		}
		return appointmentRowMapper;
	}
	
}
