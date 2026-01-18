package br.edu.ifpb.dac.chargeManager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeResponseDto {

    private Long id;
    private Long userId;
    private String name;
    private BigDecimal amount;
    private String paymentType;
    private String status;
    private String externalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
