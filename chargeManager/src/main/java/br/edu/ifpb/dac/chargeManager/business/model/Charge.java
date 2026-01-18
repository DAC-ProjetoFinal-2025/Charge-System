package br.edu.ifpb.dac.chargeManager.business.model;

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
public class Charge {

    private Long id;
    private Long userId;
    private String name;
    private BigDecimal amount;
    private String paymentType;
    private String customer;
    private String dueDate;
    private String status;
    private String externalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
