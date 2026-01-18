package br.edu.ifpb.dac.chargeProxy.infra.integration.manager;

/**
 * Cliente SOAP para comunicação com o ChargeManager
 * Permite enviar atualizações de status recebidas via webhook
 */
public interface ManagerSoapClient {

    /**
     * Atualiza o status de uma cobrança no Manager
     * 
     * @param externalId ID da cobrança no Asaas
     * @param status     Novo status da cobrança
     * @return true se atualizado com sucesso, false caso contrário
     */
    boolean updateChargeStatus(String externalId, String status);
}
