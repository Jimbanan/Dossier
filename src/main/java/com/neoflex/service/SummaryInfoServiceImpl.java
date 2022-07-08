package com.neoflex.service;

import com.neoflex.dto.SummaryAppInfoDTO;
import com.neoflex.feignclient.DealClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryInfoServiceImpl implements SummaryInfoService{

    private final DealClient dealClient;

    public SummaryAppInfoDTO getSummaryAppInfoDTODealMC(Long id) {
        return dealClient.getSummaryAppInfoFromDealMC(id);
    }
}
