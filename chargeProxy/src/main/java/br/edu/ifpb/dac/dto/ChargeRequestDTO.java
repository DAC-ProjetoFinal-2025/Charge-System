package br.edu.ifpb.dac.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ChargeRequestDTO {
    private BigDecimal amount;
    private String customerName;
}
