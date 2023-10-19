package com.telemed.dao;

import java.sql.Date;

import com.telemed.userentities.Patient;

public interface PatientDao extends UserDao{
	public Date extractDob(int primaryKey);
	public Patient extract(String email);
}
