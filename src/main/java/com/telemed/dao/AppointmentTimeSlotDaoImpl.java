package com.telemed.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.telemed.dao.rowmapper.AppointmentTimeSlotMapper;
import com.telemed.exceptions.NoSlotsAvailable;
import com.telemed.medicalhistoryentity.AppointmentTimeSlot;

@Repository
public class AppointmentTimeSlotDaoImpl {
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private AppointmentTimeSlotMapper appointmentTimeSlotMapper=AppointmentTimeSlotMapper.getRowMapper();
	
	public boolean store(AppointmentTimeSlot slot) {
		String storeSlotQuery="""
					INSERT INTO appointment_slot (id,doctor_id,start,end,total_patient,current_patient)
					VALUES(?,?,?,?,?,?)
				""";
		jdbcTemplate.update(storeSlotQuery,
							slot.getId(),
							slot.getDoctorId(),
							slot.getStartTime(),
							slot.getEndTime(),
							slot.getMaxPatientPerSlot(),
							slot.getCurrentNoOfPatient()
							);
		return true;
	}
	
	public List<AppointmentTimeSlot> extractAll() {
		String extractAppointmentTimeSlots="""
					SELECT * FROM appointment_slot
				""";
		List<AppointmentTimeSlot> slots=null;
		try {			
			slots=jdbcTemplate.query(extractAppointmentTimeSlots,appointmentTimeSlotMapper);
		} catch (EmptyResultDataAccessException e) {
			throw new NoSlotsAvailable();
		}
		return slots;
	}
	
	public List<AppointmentTimeSlot> extractAvailable(int doctorKey) {
		String extractAvailableTimeSlots="""
					SELECT * FROM appointment_slot where doctor_id=? AND 
					current_patient < total_patient AND end>CURTIME()
				""";
		List<AppointmentTimeSlot> slots=null;
		try {			
			slots=jdbcTemplate.query(extractAvailableTimeSlots,appointmentTimeSlotMapper,doctorKey);
		} catch (EmptyResultDataAccessException e) {
			throw new NoSlotsAvailable();
		}
		return slots;
	}
	
	public boolean updateSlot(int slotId) {
		String extractCurrentNoOfPatient="""
					SELECT * FROM appointment_slot where id=?
				""";
		AppointmentTimeSlot slotObject=jdbcTemplate.queryForObject(extractCurrentNoOfPatient,
																   appointmentTimeSlotMapper,slotId);
		slotObject.setCurrentNoOfPatient(slotObject.getCurrentNoOfPatient()+1);
		
		return this.store(slotObject);
	}
}
