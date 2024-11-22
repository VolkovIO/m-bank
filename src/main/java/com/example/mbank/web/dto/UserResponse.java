package com.example.mbank.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "Клиент")
public class UserResponse {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "Имя")
    private String name;

    @Schema(description = "Дата рождения")
    private LocalDate dateOfBirth;

    @Schema(description = "Баланс")
    @Min(value = 0, message = "Balance cannot be negative")
    private BigDecimal balance;

    @Schema(description = "Почтовые адреса")
    @NotEmpty(message = "Person must have at least one email in DTO")
    private Set<String> emails;

    @Schema(description = "Номера телефонов")
    @NotEmpty(message = "Person must have at least one phone in DTO")
    private Set<String> phones;

}
