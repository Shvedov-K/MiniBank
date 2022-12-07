package com.IFutureTestTask.MiniBank.service.impl;

import com.IFutureTestTask.MiniBank.service.MetricService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableScheduling
public class MetricServiceImpl implements MetricService {

    private Map<String, Integer> statusMetric;
    final static Logger LOG = Logger.getLogger(MetricServiceImpl.class);

    public MetricServiceImpl() {
        statusMetric = new ConcurrentHashMap<>();
        statusMetric.put("GET /balance/getBalance", 0);
        statusMetric.put("POST /balance/changeBalance", 0);
    }

    public void increaseCount(String request) {
        statusMetric.put(request, statusMetric.get(request) + 1);
    }

    @Scheduled(fixedDelay = 60000)
    private void exportMetrics() {
        Integer getBalanceRequestCount = statusMetric.get("GET /balance/getBalance");
        Integer changeBalanceRequestCount = statusMetric.get("POST /balance/changeBalance");
        Integer allRequestsCount = getBalanceRequestCount + changeBalanceRequestCount;
        LOG.info("Get Balance: " + getBalanceRequestCount);
        LOG.info("Change Balance: " + changeBalanceRequestCount);
        LOG.info("All request: " + allRequestsCount);
        statusMetric.replace("GET /balance/getBalance", 0);
        statusMetric.replace("POST /balance/changeBalance", 0);
    }
}
