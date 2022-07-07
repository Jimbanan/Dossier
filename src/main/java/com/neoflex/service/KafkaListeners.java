package com.neoflex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final ObjectMapper objectMapper;
    private final MailSenderImpl mailSender;

    @KafkaListener(topics = {"finish-registration", "create-documents", "send-documents",
            "send-ses", "credit-issued", "application-denied"}, groupId = "groupId")
    void listener(String str) throws JsonProcessingException {

//        EmailMessage emailMessage = objectMapper.readValue(str, EmailMessage.class);

        System.out.println("TEST");
        try {
            mailSender.sendEmail("neoflexprojectconveyor@mail.ru", "Test", "Привет, товарищи. Присылаю вам письмо...");
            System.out.println("Mail sended");
        } catch (MailException mailException) {
            System.out.println("Mail send failed.");
            mailException.printStackTrace();
        }

    }
}
