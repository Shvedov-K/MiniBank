package com.IFutureTestTask.MiniBank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
public class Balance {

    @Id
    @Column(name = "user_id")
    Long userId;
    @Column(name = "amount")
    Long amount;

    public Optional<Long> getAmount() {
        return Optional.ofNullable(amount);
    }
}
