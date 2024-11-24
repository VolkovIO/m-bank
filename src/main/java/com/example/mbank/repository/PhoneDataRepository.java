package com.example.mbank.repository;

import com.example.mbank.entity.PhoneData;
import com.example.mbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    Optional<PhoneData> findByUserAndPhone(User user, String phone);

    Long countByUser(User user);

    List<PhoneData> findByUser(User user);
}
