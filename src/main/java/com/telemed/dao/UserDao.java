package com.telemed.dao;

public interface UserDao {
	
	public String extractName(int primaryKey);
	public String extractEmail(int primaryKey);
	public String extractPhoneNo(int primaryKey);
	public String extractCity(int primaryKey);
	public String extractPassword(int primaryKey);
}
