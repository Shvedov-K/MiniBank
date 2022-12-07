package com.IFutureTestTask.MiniBank.repository;

import com.IFutureTestTask.MiniBank.model.Balance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {
    Optional<Balance> findById(Long UserId);
}
