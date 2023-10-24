package com.telemed.dao;

import java.util.List;

import com.telemed.medicalhistoryentity.Appointment;


public interface AppointmentDao {
	public List<Appointment> extractAll(int id);
}
