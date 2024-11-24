package com.example.mbank.repository;

import com.example.mbank.entity.Account;
import com.example.mbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUser (User user);
}
