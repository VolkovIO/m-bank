package com.example.mbank.web.controller;

import com.example.mbank.service.TransferService;
import com.example.mbank.web.dto.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Tag(name = "Функционал трансфера денег")
@RestController
@RequestMapping("/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @Operation(
            summary = "Трансфер денег ",
            description = "Трансфер денег от одного пользователя к другому<br>" +
                    "Отправителя определяем по JWT токену")
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "Успешно"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")})
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferRequest request, Principal principal) {
        transferService.transferMoney(principal.getName(), request.getRecipientUserId(), request.getValue());
        return ResponseEntity.ok("Transfer successful");
    }
}
