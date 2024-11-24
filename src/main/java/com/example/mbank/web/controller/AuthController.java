package com.example.mbank.web.controller;

import com.example.mbank.entity.User;
import com.example.mbank.exception.AppError;
import com.example.mbank.service.UserSecurityService;
import com.example.mbank.utils.JwtTokenUtils;
import com.example.mbank.web.dto.JwtRequest;
import com.example.mbank.web.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthController {

    private final UserSecurityService userSecurityService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Получение токена JWT",
            description = "В качестве логина может быть email или номер телефона")
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "Успешно"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")})
    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest jwtRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            AppError error = new AppError(HttpStatus.UNAUTHORIZED.value(), "Ошибка аутентификации");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = userSecurityService.findByLogin(jwtRequest.getLogin());
        String generatedToken= jwtTokenUtils.generateToken(user
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", user))
        ));
        return ResponseEntity.ok(new JwtResponse(generatedToken));
    }


}
