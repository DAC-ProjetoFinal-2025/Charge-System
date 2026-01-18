package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.api.dto.UpdateChargeStatusRequestDto;
import br.edu.ifpb.dac.chargeManager.api.dto.UpdateChargeStatusResponseDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * Serviço SOAP para receber atualizações de webhook do ChargeProxy
 */
@WebService
public interface WebhookService {

    /**
     * Atualiza o status de uma cobrança baseado no externalId (ID do Asaas)
     * 
     * @param externalId ID da cobrança no Asaas
     * @param status     Novo status da cobrança
     * @return Resposta indicando sucesso ou falha
     */
    @WebMethod
    UpdateChargeStatusResponseDto updateChargeStatus(String externalId, String status);
}
