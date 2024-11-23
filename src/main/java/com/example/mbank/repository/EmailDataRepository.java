package com.example.mbank.repository;

import com.example.mbank.entity.EmailData;
import com.example.mbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    Optional<EmailData> findByUserAndEmail(User user, String email);

    Long countByUser(User user);

    List<EmailData> findByUser(User user); // Метод для поиска всех emails по пользователю
}
