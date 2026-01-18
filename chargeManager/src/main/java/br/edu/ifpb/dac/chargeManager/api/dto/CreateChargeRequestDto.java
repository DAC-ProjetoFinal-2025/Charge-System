package br.edu.ifpb.dac.chargeManager.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChargeRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Charge name is required")
    private String name;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotBlank(message = "Customer ID is required")
    private String customer;

    @NotBlank(message = "Due date is required")
    private String dueDate;
}
