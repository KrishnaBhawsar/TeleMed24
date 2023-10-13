package com.telemed.dao;

public interface DoctorDao extends UserDao{
	public String extractCertificateNo(int primaryKey);
	public String extractSpecilzation(int primaryKey);
	public int extractRating(int primaryKey);
}
