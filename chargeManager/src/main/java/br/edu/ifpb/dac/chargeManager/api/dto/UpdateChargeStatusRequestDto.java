package br.edu.ifpb.dac.chargeManager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO para requisição de atualização de status via SOAP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChargeStatusRequestDto implements Serializable {

    private String externalId; // ID da cobrança no Asaas
    private String status; // Novo status da cobrança
}
