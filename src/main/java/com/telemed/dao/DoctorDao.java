package com.telemed.dao;

import com.telemed.userentities.Doctor;

public interface DoctorDao extends UserDao{
	public Doctor extract(String email);
	public String extractCertificateNo(int primaryKey);
	public String extractSpecialization(int primaryKey);
	public int extractRating(int primaryKey);
}
