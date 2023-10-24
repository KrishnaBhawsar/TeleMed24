package com.telemed.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.telemed.dao.rowmapper.AppointmentRowMapper;
import com.telemed.medicalhistoryentity.Appointment;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private AppointmentRowMapper appointmentRowMapper=AppointmentRowMapper.getRowMapper();

	@Override
	public List<Appointment> extractAll(int id) {
		String extractAppointmentQuery="SELECT * FROM appointment WHERE patient_id=?";
		
		List<Appointment> appointments=jdbcTemplate.query(extractAppointmentQuery, appointmentRowMapper, id);
		return appointments;
	}
		
}
