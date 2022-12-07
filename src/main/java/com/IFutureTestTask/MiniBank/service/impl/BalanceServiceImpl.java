package com.IFutureTestTask.MiniBank.service.impl;

import com.IFutureTestTask.MiniBank.model.Balance;
import com.IFutureTestTask.MiniBank.repository.BalanceRepository;
import com.IFutureTestTask.MiniBank.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    @Cacheable(value = "balance")
    public Optional<Long> getBalance(Long userId) {
        Balance balance = balanceRepository.findById(userId).orElse(null);

        // Сделано для проверки существования юзера с таким id и использовалось бы в
        // контроллере. Но, так как нам более важна производительность, а так же не
        // предусмотрены запросы с ошибками, то закомментировано до появления таких запросов
        //if (balance == null) return null;

        return balance.getAmount();
    }

    @Override
    @CacheEvict(value = "balance")
    public void changeBalance(Long userId, Long newAmount) {
        Balance balance = balanceRepository.findById(userId).orElse(null);
        balance.setAmount(newAmount);
        balanceRepository.save(balance);
    }
}
