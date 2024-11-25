package com.example.mbank.service;

import com.example.mbank.entity.Account;
import com.example.mbank.entity.User;
import com.example.mbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceUpdaterService {
    private final UserRepository userRepository;
    private final Map<Long, BigDecimal> initialBalances = new HashMap<>();

    // Метод для инициализации начальных значений баланса
    @PostConstruct
    public void initializeInitialBalances() {
        List<User> users = userRepository.findAll();
        for (User  user : users) {
            Account account = user.getAccount();
            if (account != null) {
                initialBalances.put(account.getId(), account.getBalance());
                log.info("Инициализирован начальный баланс для пользователя {}: {}", user.getId(), account.getBalance());
            }
        }
    }

    // Метод, который будет вызываться каждые 30 секунд
    @Transactional
    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void updateBalances() {
        List<User> users = userRepository.findAll();

        for (User  user : users) {
            Account account = user.getAccount();
            if (account != null) {
                if (isBalanceExceedingLimit(account)) {
                    log.info("Баланс для пользователя {} превышает 207%, обновление пропущено.", user.getId());
                    continue; // Пропустить обновление для этого пользователя
                }
                updateBalance(account);
            }
        }
    }

    private boolean isBalanceExceedingLimit(Account account) {
        // Проверяем, превышает ли текущий баланс 207% от начального значения
        BigDecimal currentBalance = account.getBalance();
        BigDecimal initialBalance = initialBalances.get(account.getId());
        BigDecimal limit = initialBalance.multiply(BigDecimal.valueOf(2.07)).setScale(2, RoundingMode.HALF_UP);

        log.info("Проверка баланса для пользователя {}: текущий: {}, лимит: {}", account.getUser ().getId(), currentBalance, limit);

        return currentBalance.compareTo(limit) >= 0;
    }

    private void updateBalance(Account account) {
        BigDecimal initialDeposit = initialBalances.get(account.getId()); // Начальный баланс
        BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07)) // 207% от начального баланса
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal increment = account.getBalance().multiply(BigDecimal.valueOf(0.10)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal newBalance = account.getBalance().add(increment).setScale(2, RoundingMode.HALF_UP);

        if (newBalance.compareTo(maxBalance) > 0) {
            newBalance = maxBalance;
        }

        account.setBalance(newBalance);
        log.info("Обновленный баланс для пользователя  {}: {}", account.getUser().getId(), newBalance);
    }
}
