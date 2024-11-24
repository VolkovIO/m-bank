package com.example.mbank.service;

import com.example.mbank.entity.Account;
import com.example.mbank.entity.User;
import com.example.mbank.repository.AccountRepository;
import com.example.mbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceUpdaterService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final Map<Long, BigDecimal> initialBalances = new HashMap<>();

    // Метод, который будет вызываться каждые 30 секунд
    @Transactional
    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void updateBalances() {
        List<User> users = userRepository.findAll();

        for (User  user : users) {
            Account account = user.getAccount();
            if (account != null) {
                updateBalance(account);
            }
        }
    }

    private void updateBalance(Account account) {
        initialBalances.putIfAbsent(account.getId(), account.getBalance());

        BigDecimal initialDeposit = initialBalances.get(account.getId()); // Начальный баланс
        BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07)); // 207% от начального баланса

        BigDecimal increment = account.getBalance().multiply(BigDecimal.valueOf(0.10));
        BigDecimal newBalance = account.getBalance().add(increment);

        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }

        account.setBalance(newBalance);
        log.info("Обновленный баланс для пользователя  {}: {}", account.getUser().getId(), newBalance);
    }
}
