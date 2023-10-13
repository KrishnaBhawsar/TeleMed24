package com.telemed.userentities;

import org.springframework.stereotype.Component;

import com.telemed.medicalhistoryentity.Appointment;

@Component
public class Doctor extends User{
	private String certificateNo;
	private int rating;
	private String specialization;
	
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	

	@Override
	public String toString() {
		return "Doctor [certificateNo=" + certificateNo + ", rating=" + rating + ", specialization=" + specialization
				+ ", id=" + id + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo + ", city=" + city
				+ ", appointments=" + appointments + "]";
	}
	public Doctor(String certificateNo, int rating, String specialization) {
		super();
		this.certificateNo = certificateNo;
		this.rating = rating;
		this.specialization = specialization;
	}
	public Doctor() {
		super();
	}
	public Doctor(int id, String name, String email, String phoneNo, String city, Appointment appointment) {
		super(id, name, email, phoneNo, city, appointment);
	}
	
	
}
