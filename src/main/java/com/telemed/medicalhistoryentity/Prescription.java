package com.telemed.medicalhistoryentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.telemed.storeentity.Product;

public class Prescription {
	List<Product> medicines;
	String[] advice;
	
	public Prescription() {
		super();
		medicines=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	public Prescription(Product medicine, String[] advice) {
		super();
		this.medicines.add(medicine);
		this.advice = advice;
	}

	public List<Product> getMedicines() {
		return medicines;
	}
	
	public void setMedicines(Product medicine) {
		this.medicines.add(medicine);
	}
	
	public String[] getAdvice() {
		return advice;
	}
	
	public void setAdvice(String[] advice) {
		this.advice = advice;
	}
	
	@Override
	public String toString() {
		return "Prescription [advice=" + Arrays.toString(advice) + "]";
	}
}
