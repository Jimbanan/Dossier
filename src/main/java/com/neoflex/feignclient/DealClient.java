package com.neoflex.feignclient;

import com.neoflex.dto.SummaryAppInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="deal-service-client", url = "${services.deal.url}")
public interface DealClient {

    @PostMapping("/deal/application/{applicationId}")
    SummaryAppInfoDTO getSummaryAppInfoFromDealMC(@PathVariable Long applicationId);

}
