package com.neoflex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflex.dto.EmailMessageDTO;
import com.neoflex.feignclient.DealClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private static final String MESSAGE_SEND = "Сообщение успешно отправлено";
    private static final String MESSAGE_NOT_SEND = "Сообщение успешно отправлено";

    private final ObjectMapper objectMapper;
    private final MailSenderImpl mailSender;
    private final DealClient dealClient;

    @KafkaListener(topics = {"finish-registration", "create-documents", "send-documents",
            "send-ses", "credit-issued", "application-denied"}, groupId = "groupId")
    void listener(String str) throws JsonProcessingException {

        EmailMessageDTO emailMessageDTO = objectMapper.readValue(str, EmailMessageDTO.class);
        StringBuilder body = new StringBuilder();

        log.info("listener() - void: Отправка по адресу: " + emailMessageDTO.getAddress() + " Тема: " + emailMessageDTO.getTheme());

        switch (emailMessageDTO.getTheme()) {
            case FINISH_REGISTRATION: {
                try {
                    body.append("Заявка № ")
                            .append(emailMessageDTO.getApplicationId())
                            .append(" успешно зарегистрирована\n")
                            .append("\nПерейдите по ссылке для продолжения!")
                            .append("\nhttp://localhost:8085/swagger-ui/index.html#/deal-controller/finishRegistration");
                    mailSender.sendEmail(emailMessageDTO.getAddress(), "FINISH REGISTRATION", body.toString());
                    log.info(MESSAGE_SEND);
                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

            case CREATE_DOCUMENTS: {
                try {
                    body.append("Заявка № ")
                            .append(emailMessageDTO.getApplicationId())
                            .append(" прошла проверку\n")
                            .append("\nПерейдите по ссылке для продолжения!")
                            .append("\nhttp://localhost:8085/swagger-ui/index.html#/deal-controller/sendDocs");
                    mailSender.sendEmail(emailMessageDTO.getAddress(), "CREATE DOCUMENTS", body.toString());
                    log.info(MESSAGE_SEND);
                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

            case SEND_DOCUMENTS: {
                try {
                    body.append("Документы по заявке № ")
                            .append(emailMessageDTO.getApplicationId())
                            .append("\n")
                            .append("Проверьте правильность документов и пройдите по ссылке для запроса SES кода: ")
                            .append("\nhttp://localhost:8085/swagger-ui/index.html#/deal-controller/singDocs");
                    mailSender.sendEmailWithAttachment(emailMessageDTO.getAddress(), "SEND DOCUMENTS", body.toString(), emailMessageDTO.getApplicationId());
                    log.info(MESSAGE_SEND);
                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

            case SEND_SES: {
                try {
                    Integer sesCode = dealClient.getSummaryAppInfoFromDealMC(emailMessageDTO.getApplicationId()).getSesCode();
                    body.append("SES код для заявки № ")
                            .append(emailMessageDTO.getApplicationId())
                            .append("\nSES код: ")
                            .append(sesCode)
                            .append("\n")
                            .append("Пройдите по ссылке и введите SES код: ")
                            .append("\nhttp://localhost:8085/swagger-ui/index.html#/deal-controller/receiveSesCode");
                    mailSender.sendEmail(emailMessageDTO.getAddress(), "SEND SES", body.toString());
                    log.info(MESSAGE_SEND);
                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

            case CREDIT_ISSUED: {
                try {
                    body.append("Кредит успешно выдан!\n")
                            .append("\nДеньги поступят на счет в течении 24 часов!");
                    mailSender.sendEmail(emailMessageDTO.getAddress(), "CREDIT ISSUED", body.toString());
                    log.info(MESSAGE_SEND);

                    log.info("\n\n");
                    log.info("-----------------------------|");
                    log.info("|          (づ｡◕‿‿◕｡)づ       |");
                    log.info("|ヽ༼ຈل͜ຈ༽ﾉ Я Сделяль ヽ༼ຈل͜ຈ༽ﾉ |");
                    log.info("|(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ ✧ﾟ･: *ヽ(◕ヮ◕ヽ)|");
                    log.info("|----------------------------|");

                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

            case APPLICATION_DENIED: {
                try {
                    body.append("Заявка на кредит №")
                            .append(emailMessageDTO.getApplicationId())
                            .append(" отклонена");
                    mailSender.sendEmail(emailMessageDTO.getAddress(), "APPLICATION DENIED", body.toString());
                    log.info(MESSAGE_SEND);

                    log.info("\n\n");
                    log.info("-----------------------------|");
                    log.info("|          (づ｡◕‿‿◕｡)づ       |");
                    log.info("|ヽ༼ຈل͜ຈ༽ﾉ Я Сделяль ヽ༼ຈل͜ຈ༽ﾉ |");
                    log.info("|(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ ✧ﾟ･: *ヽ(◕ヮ◕ヽ)|");
                    log.info("|----------------------------|");
                } catch (MailException mailException) {
                    log.info(MESSAGE_NOT_SEND);
                    mailException.printStackTrace();
                }
                break;
            }

        }


    }
}

