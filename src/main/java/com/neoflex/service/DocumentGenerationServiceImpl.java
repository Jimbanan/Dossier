package com.neoflex.service;

import com.neoflex.dto.PaymentScheduleElementDTO;
import com.neoflex.dto.SummaryAppInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

    public File createCreditApplicationDocument(SummaryAppInfoDTO summaryInfo, Long id) {

        StringBuilder stringBuilder = new StringBuilder("КРЕДИТНАЯ ЗАЯВКА № " + id + " от " + LocalDateTime.now())
                .append("\n\nИнформация о клиенте")
                .append("\n\tПолное имя: ").append(summaryInfo.getFullName())
                .append("\n\tДата рождения: ").append(summaryInfo.getBirthdate())
                .append("\n\tПол: ").append(summaryInfo.getGender())
                .append("\n\tПаспорт: ").append(summaryInfo.getFullPassportData())
                .append("\n\tEmail: ").append(summaryInfo.getEmail())
                .append("\n\tСемейное положение: ").append(summaryInfo.getMartialStatus())
                .append("\n\tКоличество иждивенцев: ").append(summaryInfo.getDependentAmount())
                .append("\n\nИнформация о работе клиента")
                .append("\n\tРабочий статус: ").append(summaryInfo.getEmploymentDTO().getEmploymentStatus())
                .append("\n\tЗарплата: ").append(summaryInfo.getEmploymentDTO().getSalary())
                .append("\n\tДолжность: ").append(summaryInfo.getEmploymentDTO().getPosition())
                .append("\n\tОпыт работы (общий): ").append(summaryInfo.getEmploymentDTO().getWorkExperienceTotal())
                .append("\n\tОпыт работы (текущий): ").append(summaryInfo.getEmploymentDTO().getWorkExperienceCurrent());

        File credit_application = null;

        try {
            credit_application = File.createTempFile("credit_application", ".txt", new File("src/main/resources"));

            FileWriter fileWriter = new FileWriter(credit_application);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return credit_application;
    }

    public File createCreditContractDocument(SummaryAppInfoDTO summaryInfo, Long id) {

        StringBuilder stringBuilder = new StringBuilder("КРЕДИТНАЯ ЗАЯВКА № " + id + " от " + LocalDateTime.now())
                .append("\n\nПолное имя клиента: ").append(summaryInfo.getFullName())
                .append("\nПаспорт клиента: ").append(summaryInfo.getFullPassportData())
                .append("\n\nИнформация о работе кредите")
                .append("\n\tСумма кредита: ").append(summaryInfo.getAmount().toString())
                .append("\n\tСрок кредита: ").append(summaryInfo.getTerm())
                .append("\n\tЕжемесячный платёж: ").append(summaryInfo.getMonthlyPayment())
                .append("\n\tПроцентная ставка: ").append(summaryInfo.getRate())
                .append("\n\tПолная стоимость кредита: ").append(summaryInfo.getPsk())
                .append("\n\tСтраховка включена: ").append(summaryInfo.getIsInsuranceEnabled())
                .append("\n\tЗарплатный клиент: ").append(summaryInfo.getIsInsuranceEnabled());

        File credit_contract = null;

        try {
            credit_contract = File.createTempFile("credit_contract", ".txt", new File("src/main/resources"));

            FileWriter fileWriter = new FileWriter(credit_contract);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return credit_contract;
    }

    public File createCreditPaymentScheduleDocument(SummaryAppInfoDTO summaryInfo, Long id) {

        StringBuilder stringBuilder = new StringBuilder("Ежемесячные платежи по договору № " + id + " от " + LocalDateTime.now());

        for (PaymentScheduleElementDTO paymentScheduleElementDTO : summaryInfo.getPaymentScheduleElementDTOList()) {
            stringBuilder.append("\nМесяц платежа № ").append(paymentScheduleElementDTO.getNumber())
                    .append("\n\tДата платежа: ").append(paymentScheduleElementDTO.getDate().toString())
                    .append("\n\tМесячный платёж: ").append(paymentScheduleElementDTO.getTotalPayment())
                    .append("\n\tПогашение процентов: ").append(paymentScheduleElementDTO.getInterestPayment())
                    .append("\n\tПогашение основного долга: ").append(paymentScheduleElementDTO.getDebtPayment())
                    .append("\n\tОстаток долга: ").append(paymentScheduleElementDTO.getRemainingDebt());
        }

        File credit_payment_schedule = null;

        try {
            credit_payment_schedule = File.createTempFile("credit_payment_schedule", ".txt", new File("src/main/resources"));

            FileWriter fileWriter = new FileWriter(credit_payment_schedule);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return credit_payment_schedule;
    }

}
