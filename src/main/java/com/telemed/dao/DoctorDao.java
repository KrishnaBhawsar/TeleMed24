package com.telemed.dao;

public interface DoctorDao extends UserDao{
	public String extractCertificateNo(int primaryKey);
	public String extractSpecialization(int primaryKey);
	public int extractRating(int primaryKey);
}
