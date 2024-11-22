package com.example.mbank.repository;

import com.example.mbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.account a LEFT JOIN FETCH u.emails e LEFT JOIN FETCH u.phones p")
    List<User> findAllWithDetails();
}
