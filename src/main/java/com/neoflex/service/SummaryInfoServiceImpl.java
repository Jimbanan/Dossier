package com.neoflex.service;

import com.neoflex.dto.SummaryAppInfoDTO;
import com.neoflex.feignclient.DealClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryInfoServiceImpl implements SummaryInfoService {

    private final DealClient dealClient;

    public SummaryAppInfoDTO getSummaryAppInfoDTODealMC(Long id) {
        log.info("getSummaryAppInfoDTODealMC() - SummaryAppInfoDTO: SummaryAppInfoDTO получен");
        return dealClient.getSummaryAppInfoFromDealMC(id);
    }
}
