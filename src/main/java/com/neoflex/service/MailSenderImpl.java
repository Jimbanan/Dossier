package com.neoflex.service;

import com.neoflex.dto.SummaryAppInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class MailSenderImpl implements MailSender {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private DocumentGenerationServiceImpl documentGenerationService;

    @Autowired
    private SummaryInfoServiceImpl summaryInfoServiceImpl;

    private SimpleMailMessage templateMessage = new SimpleMailMessage();

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setFrom("neoflexprojectconveyor@mail.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
        log.info("sendEmail() - void: Сообщение отправлено");
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String text, Long id) {
        SummaryAppInfoDTO summaryInfo = summaryInfoServiceImpl.getSummaryAppInfoDTODealMC(id);

        File credit_application = documentGenerationService.createCreditApplicationDocument(summaryInfo, id);
        File credit_contract = documentGenerationService.createCreditContractDocument(summaryInfo, id);
        File credit_payment_schedule = documentGenerationService.createCreditPaymentScheduleDocument(summaryInfo, id);
        log.info("sendEmailWithAttachment() - void: Документы сформированы");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("neoflexprojectconveyor@mail.ru");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(credit_application.getName(), credit_application);
            helper.addAttachment(credit_contract.getName(), credit_contract);
            helper.addAttachment(credit_payment_schedule.getName(), credit_payment_schedule);
            javaMailSender.send(message);
            log.info("sendEmailWithAttachment() - void: Сообщение с документами отправлено");
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            log.info("sendEmailWithAttachment() - void: Удаление сформированных документов");
            credit_application.delete();
            credit_contract.delete();
            credit_payment_schedule.delete();
            log.info("sendEmailWithAttachment() - void: Документы удалены");
        }
    }
}