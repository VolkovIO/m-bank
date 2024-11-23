package com.example.mbank.utils;

import com.example.mbank.entity.EmailData;
import com.example.mbank.entity.PhoneData;
import com.example.mbank.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;

public class UserSpecifications {

    public static Specification<User> hasDateOfBirthAfter(LocalDate dateOfBirth) {
        return (root, query, builder) -> builder.greaterThan(root.get("dateOfBirth"), dateOfBirth);
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, builder) -> {
            Join<User, PhoneData> phones = root.join("phones", JoinType.INNER);
            return builder.equal(phones.get("phone"), phone);
        };
    }

    public static Specification<User> hasNameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), name + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, builder) -> {
            Join<User, EmailData> emails = root.join("emails", JoinType.INNER);
            return builder.equal(emails.get("email"), email);
        };
    }
}
