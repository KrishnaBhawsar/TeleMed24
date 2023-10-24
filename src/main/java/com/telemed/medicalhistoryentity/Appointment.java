package com.telemed.medicalhistoryentity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Appointment {
	private int id;
	private int patientId;
	private int doctorId;
	private Date date;
	private Prescription prescription;
	
	public Appointment(int id, int patientId, int doctorId, Date date, Prescription prescription) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.date = date;
		this.prescription = prescription;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient_id=" + patientId + ", doctorId=" + doctorId + ", date=" + date
				+ ", prescription=" + prescription + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patient_id) {
		this.patientId = patient_id;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
}
