package com.example.mbank.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull(message = "Recipient user ID cannot be null")
    private Long recipientUserId;

    @NotNull(message = "Transfer value cannot be null")
    @Positive(message = "Transfer value must be positive")
    @Schema(description = "Amount to transfer, must be a positive number", example = "100.00")
    private BigDecimal value;
}
