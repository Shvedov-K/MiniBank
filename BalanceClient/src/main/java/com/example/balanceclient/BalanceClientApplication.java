package com.example.balanceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class BalanceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalanceClientApplication.class, args);
        test();
    }

    static int threadCount = 10;
    static Double readQuota = 0.5;
    static Double writeQuota = 0.5;
    static List<Integer> readIdList = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    static List<Integer> writeIdList = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

    //Это конечно ужас, но вроде не является основной целью тестового
    public static void test() {
        Runnable r = ()-> {
            RestTemplate restTemplate = new RestTemplate();
            while (true) {
                double readProbability = readQuota/(readQuota+writeQuota);
                try {
                    if (ThreadLocalRandom.current().nextDouble() < readProbability) {
                        getBalance(restTemplate);
                    } else {
                        changeBalance(restTemplate);
                    }
                } catch (Exception e) {}
            }
        };
        for (int i = 0; i < threadCount; i++) {
            new Thread(r, "TestThread " + i).start();
        }
    }

    private static void getBalance(RestTemplate restTemplate) throws Exception {
        Integer randomUserId = readIdList.get(ThreadLocalRandom.current().nextInt(0, 8));
        restTemplate.getForObject("http://localhost:8080/balance/getBalance?userId=" + randomUserId, String.class);
    }

    private static void changeBalance(RestTemplate restTemplate) throws Exception {
        Integer randomUserId = writeIdList.get(ThreadLocalRandom.current().nextInt(0, 8));
        Long amount = ThreadLocalRandom.current().nextLong(0,10000);
        restTemplate.postForLocation("http://localhost:8080/balance/changeBalance?userId=" + randomUserId + "&amount=" + amount, null);
    }

}
