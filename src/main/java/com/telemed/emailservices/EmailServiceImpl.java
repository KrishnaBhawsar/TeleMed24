package com.telemed.emailservices;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public String sendOpt(String to) {
		
		// OTP generation code
		int otp=1;
		int min=2000,max=6000;
		Random random=new Random();
		otp=random.nextInt(min,max);
		
		// OTP send code
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("TeleMed24 sign-up OTP");
		message.setText("Your OTP is "+otp);
		mailSender.send(message);
		
		System.out.println(otp);
		return otp+"";
	}

}
