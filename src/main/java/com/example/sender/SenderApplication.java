package com.example.sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.example.sender.SendEmail;

@SpringBootApplication
public class SenderApplication {

	@Autowired
	private SendEmail sendEmail;

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerWhenStarts(){
		sendEmail.sendEmail("juniorferreira59@outlook.com","Olá, isso é um teste","Test Java");
	}

}
