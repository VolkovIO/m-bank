package com.example.mbank.repository;

import com.example.mbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.account a LEFT JOIN FETCH u.emails e LEFT JOIN FETCH u.phones p")
    List<User> findAllWithDetails();

    Optional<User> findByName(String name);

    @Query("SELECT u FROM User u JOIN u.emails e WHERE e.email = :login OR EXISTS (SELECT p FROM u.phones p WHERE p.phone = :login)")
    Optional<User> findByLogin(@Param("login") String login);
}
