package br.edu.ifpb.dac.chargeManager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO para resposta de atualização de status via SOAP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChargeStatusResponseDto implements Serializable {

    private boolean success; // Indica se a atualização foi bem-sucedida
    private String message; // Mensagem descritiva
}
