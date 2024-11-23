package com.example.mbank.web.controller;

import com.example.mbank.service.UserService;
import com.example.mbank.web.dto.UpdateUserContactRequest;
import com.example.mbank.web.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Взаимодействие с пользователями")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Возвращает всех пользователей",
            description = "Возвращает всех пользователей из БД")
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "Успешно"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")})
    @GetMapping("/all")
    List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Обновление контактных данных пользователя",
            description = "Добавляет, удаляет список телефон, emails у пользователя. Последний телефон, емайл не удаляется")
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "Успешно создан клиент"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")})
    @PostMapping("/updateUserContacts")
    public ResponseEntity<Void> updateUserContacts(@RequestBody UpdateUserContactRequest request) {
        userService.updateUserContacts(request);
        return ResponseEntity.ok().build();
    }
}
