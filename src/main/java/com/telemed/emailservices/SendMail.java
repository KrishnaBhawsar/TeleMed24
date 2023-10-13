package com.telemed.emailservices;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMail {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping("/otp")
	public String sendOtp(@RequestParam("name") String name,@RequestParam("emailId") String to) {
		
		//Otp generation code
		int min=2000,max=6000;
		int otp=1;
		Random random=new Random();
		otp=random.nextInt(min,max);  // this will generate otp between min and max

		// Email sending mechanism
		String otpText="Your OTP is "+otp;
		SimpleMailMessage mailMessage=new SimpleMailMessage();
//		mailMessage.setTo("krishnabhawsar8@gmail.com");
		mailMessage.setTo(to);
		mailMessage.setText(otpText);
		mailSender.send(mailMessage);
		
		return otp+"";
	}
}
