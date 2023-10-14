package com.telemed.emailservices;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public String sendOtp(String to) {
		
		//Otp generation code
		int min=2000,max=6000;
		int otp=1;
		Random random=new Random();
		otp=random.nextInt(min,max);  // this will generate otp between min and max

		System.out.println("Sending mail");
//		Email sending mechanism
		String otpText="Your OTP is "+otp;
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setText(otpText);
		mailSender.send(mailMessage);
		
		return otp+"";
	}
}
