package br.edu.ifpb.dac.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ChargeDTO {
    private Long id;
    private BigDecimal amount;
    private String customerName;
    private String status;
}