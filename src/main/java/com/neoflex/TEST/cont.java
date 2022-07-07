package com.neoflex.TEST;

import com.neoflex.service.MailSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cont {

    @Autowired
    public MailSenderImpl mailSender;

    @GetMapping("/cont/test")
    void sending() {
        try {
            mailSender.sendEmail("neoflexprojectconveyor@mail.ru", "Test", "Привет, товарищи. Присылаю вам письмо...");
            System.out.println("Mail sended");
        }catch (MailException mailException){
            System.out.println("Mail send failed.");
            mailException.printStackTrace();
        }

    }

}
