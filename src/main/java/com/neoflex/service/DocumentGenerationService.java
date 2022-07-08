package com.neoflex.service;

import com.neoflex.dto.SummaryAppInfoDTO;

import java.io.File;

public interface DocumentGenerationService {

    File createCreditApplicationDocument(SummaryAppInfoDTO summaryInfo, Long id);

    File createCreditContractDocument(SummaryAppInfoDTO summaryInfo, Long id);

    File createCreditPaymentScheduleDocument(SummaryAppInfoDTO summaryInfo, Long id);

}
