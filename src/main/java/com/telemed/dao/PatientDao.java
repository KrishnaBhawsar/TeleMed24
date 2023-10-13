package com.telemed.dao;

import java.sql.Date;

public interface PatientDao extends UserDao{
	public Date extractDob(int primaryKey);
}
