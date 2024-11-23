package com.example.mbank.service;

import com.example.mbank.entity.EmailData;
import com.example.mbank.entity.PhoneData;
import com.example.mbank.entity.User;
import com.example.mbank.mapper.UserMapper;
import com.example.mbank.repository.EmailDataRepository;
import com.example.mbank.repository.PhoneDataRepository;
import com.example.mbank.repository.UserRepository;
import com.example.mbank.web.dto.UpdateUserContactRequest;
import com.example.mbank.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhoneDataRepository phoneDataRepository;
    private final EmailDataRepository emailDataRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        List<User> persons = userRepository.findAllWithDetails();
        return userMapper.toDtoList(persons);
    }

    @Transactional
    public void updateUserContacts(UpdateUserContactRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));


        // Добавление телефонов
        if (request.getAddPhones() != null && !request.getAddPhones().isEmpty()) {
            Set<PhoneData> existingPhones = new HashSet<>(phoneDataRepository.findByUser(user));
            Set<PhoneData> newPhones = request.getAddPhones().stream()
                    .map(phone -> {
                        PhoneData phoneData = new PhoneData();
                        phoneData.setPhone(phone);
                        phoneData.setUser(user);
                        return phoneData;
                    })
                    .collect(Collectors.toSet());
            existingPhones.addAll(newPhones);
            phoneDataRepository.saveAll(existingPhones);
        }

        // Удаление телефонов, но оставляем последний
        if (request.getRemovePhones() != null && !request.getRemovePhones().isEmpty()) {
            Set<PhoneData> existingPhones = new HashSet<>(phoneDataRepository.findByUser(user));
            List<PhoneData> phonesToDelete = request.getRemovePhones().stream()
                    .map(phone -> phoneDataRepository.findByUserAndPhone(user, phone))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            int existingPhonesCount = existingPhones.size();
            int phonesToDeleteCount = phonesToDelete.size();

            if (existingPhonesCount - phonesToDeleteCount > 1) {
                phoneDataRepository.deleteAll(phonesToDelete);
            } else if (existingPhonesCount - phonesToDeleteCount == 1) {
                // Оставляем последний телефон
                phoneDataRepository.deleteAll(phonesToDelete.subList(0, phonesToDeleteCount - 1));
            }
        }

        // Добавление email
        if (request.getAddEmails() != null && !request.getAddEmails().isEmpty()) {
            Set<EmailData> existingEmails = new HashSet<>(emailDataRepository.findByUser(user));
            Set<EmailData> newEmails = request.getAddEmails().stream()
                    .map(email -> {
                        EmailData emailData = new EmailData();
                        emailData.setEmail(email);
                        emailData.setUser(user);
                        return emailData;
                    })
                    .collect(Collectors.toSet());
            existingEmails.addAll(newEmails);
            emailDataRepository.saveAll(existingEmails);
        }

        // Удаление email, но оставляем последний
        if (request.getRemoveEmails() != null && !request.getRemoveEmails().isEmpty()) {
            Set<EmailData> existingEmails = new HashSet<>(emailDataRepository.findByUser(user));
            List<EmailData> emailsToDelete = request.getRemoveEmails().stream()
                    .map(email -> emailDataRepository.findByUserAndEmail(user, email))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            int existingEmailsCount = existingEmails.size();
            int emailsToDeleteCount = emailsToDelete.size();

            if (existingEmailsCount - emailsToDeleteCount > 1) {
                emailDataRepository.deleteAll(emailsToDelete);
            } else if (existingEmailsCount - emailsToDeleteCount == 1) {
                // Оставляем последний телефон
                emailDataRepository.deleteAll(emailsToDelete.subList(0, emailsToDeleteCount - 1));
            }
        }
    }


}
