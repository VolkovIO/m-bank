package com.example.mbank.service;

import com.example.mbank.entity.User;
import com.example.mbank.mapper.UserMapper;
import com.example.mbank.repository.UserRepository;
import com.example.mbank.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        List<User> persons = userRepository.findAllWithDetails();
        return userMapper.toDtoList(persons);
    }
}
