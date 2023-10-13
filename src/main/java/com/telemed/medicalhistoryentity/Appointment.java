package com.telemed.medicalhistoryentity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Appointment {
	private int id;
	private int patient_id;
	private int doctorId;
	private Date date;
	private Prescription prescription;
	
	public Appointment(int id, int patient_id, int doctorId, Date date, Prescription prescription) {
		super();
		this.id = id;
		this.patient_id = patient_id;
		this.doctorId = doctorId;
		this.date = date;
		this.prescription = prescription;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient_id=" + patient_id + ", doctorId=" + doctorId + ", date=" + date
				+ ", prescription=" + prescription + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
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
