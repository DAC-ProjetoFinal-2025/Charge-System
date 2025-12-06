package br.edu.ifpb.dac.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ChargeManagerDTO {
    private BigDecimal amount;
    private String customerName;
    private String status;
}
