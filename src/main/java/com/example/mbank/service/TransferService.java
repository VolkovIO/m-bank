package com.example.mbank.service;

import com.example.mbank.entity.User;
import com.example.mbank.exception.InsufficientBalanceException;
import com.example.mbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final UserRepository userRepository;

    @Transactional
    public void transferMoney(String username, Long recipientId, BigDecimal value) {
        User senderUser  = userRepository.findByName(username)
                .orElseThrow(() -> new EntityNotFoundException("Sender user for transferMoney not found"));

        User recipientUser  = userRepository.findById(recipientId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient user for transferMoney not found"));

        if (senderUser.equals(recipientUser)) {
            throw new IllegalArgumentException("Cannot transfer money to yourself");
        }

        if (senderUser.getAccount().getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Выполнение трансфера
        BigDecimal newSenderBalance = senderUser.getAccount().getBalance().subtract(value);
        BigDecimal newRecipientBalance = recipientUser.getAccount().getBalance().add(value);

        senderUser.getAccount().setBalance(newSenderBalance);
        recipientUser.getAccount().setBalance(newRecipientBalance);
    }
}
