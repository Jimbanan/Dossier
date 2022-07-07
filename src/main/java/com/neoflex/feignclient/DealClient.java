package com.neoflex.feignclient;

import com.neoflex.dto.SummaryAppInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="deal-service-client", url = "${URL.DealMC}")
public interface DealClient {

    @PostMapping("/deal/application/{applicationId}")
    SummaryAppInfo getSummaryAppInfoFromDealMC(@PathVariable Long applicationId);

}
