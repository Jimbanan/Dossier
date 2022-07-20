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
        log.info("createCreditApplicationDocument() - File: Формирование документа <credit_application>");

        StringBuilder stringBuilder = new StringBuilder("Кредитная заявка № " + id + " от " + LocalDateTime.now())
                .append("\n\nИнформация о клиенте")
                .append("\n\tПолное имя: ").append(summaryInfo.getFullName())
                .append("\n\tДата рождения: ").append(summaryInfo.getBirthdate())
                .append("\n\tПол: ").append(summaryInfo.getGender())
                .append("\n\tПаспорт: ").append(summaryInfo.getFullPassportData())
                .append("\n\tEmail: ").append(summaryInfo.getEmail())
                .append("\n\tСемейное положение: ").append(summaryInfo.getMartialStatus())
                .append("\n\tКоличество иждивенцев: ").append(summaryInfo.getDependentAmount())
                .append("\n\nИнформация о работе клиента")
                .append("\n\tРабочий статус: ").append(summaryInfo.getEmployment().getEmploymentStatus())
                .append("\n\tЗарплата: ").append(summaryInfo.getEmployment().getSalary())
                .append("\n\tДолжность: ").append(summaryInfo.getEmployment().getPosition())
                .append("\n\tОпыт работы (общий): ").append(summaryInfo.getEmployment().getWorkExperienceTotal())
                .append("\n\tОпыт работы (текущий): ").append(summaryInfo.getEmployment().getWorkExperienceCurrent());

        log.info("createCreditApplicationDocument() - File: Содержание документа <credit_application> сформировано");

        File creditApplication = null;
        FileWriter fileWriter = null;
        try {
            creditApplication = File.createTempFile("credit_application", ".txt");

            fileWriter = new FileWriter(creditApplication);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            log.info("createCreditApplicationDocument() - File: Документ <credit_application> сформирован и сохранен");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return creditApplication;
    }

    public File createCreditContractDocument(SummaryAppInfoDTO summaryInfo, Long id) {
        log.info("createCreditContractDocument() - File: Формирование документа <credit_contract>");

        StringBuilder stringBuilder = new StringBuilder("Кредитная заявка № " + id + " от " + LocalDateTime.now())
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

        log.info("createCreditContractDocument() - File: Содержание документа <credit_contract> сформировано");

        File creditContract = null;

        try {
            creditContract = File.createTempFile("credit_contract", ".txt");

            FileWriter fileWriter = new FileWriter(creditContract);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();
            log.info("createCreditContractDocument() - File: Документ <credit_contract> сформирован и сохранен");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return creditContract;
    }

    public File createCreditPaymentScheduleDocument(SummaryAppInfoDTO summaryInfo, Long id) {
        log.info("createCreditPaymentScheduleDocument() - File: Формирование документа <credit_payment_schedule>");

        StringBuilder stringBuilder = new StringBuilder("Ежемесячные платежи по договору № " + id + " от " + LocalDateTime.now());

        for (PaymentScheduleElementDTO paymentScheduleElementDTO : summaryInfo.getPaymentScheduleElementList()) {
            stringBuilder.append("\nМесяц платежа № ").append(paymentScheduleElementDTO.getNumber())
                    .append("\n\tДата платежа: ").append(paymentScheduleElementDTO.getDate().toString())
                    .append("\n\tЕжемесячный платёж: ").append(paymentScheduleElementDTO.getTotalPayment())
                    .append("\n\tПогашение процентов: ").append(paymentScheduleElementDTO.getInterestPayment())
                    .append("\n\tПогашение основного долга: ").append(paymentScheduleElementDTO.getDebtPayment())
                    .append("\n\tОстаток долга: ").append(paymentScheduleElementDTO.getRemainingDebt());
        }

        log.info("createCreditPaymentScheduleDocument() - File: Содержание документа <credit_payment_schedule> сформировано");

        File creditPaymentSchedule = null;

        try {
            creditPaymentSchedule = File.createTempFile("credit_payment_schedule", ".txt");

            FileWriter fileWriter = new FileWriter(creditPaymentSchedule);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();
            log.info("createCreditPaymentScheduleDocument() - File: Документ <credit_payment_schedule> сформирован и сохранен");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return creditPaymentSchedule;
    }

}