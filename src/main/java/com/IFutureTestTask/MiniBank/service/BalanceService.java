package com.IFutureTestTask.MiniBank.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

public interface BalanceService {
    Optional<Long> getBalance(Long userId);
    void changeBalance(Long userId, Long amount);
}
