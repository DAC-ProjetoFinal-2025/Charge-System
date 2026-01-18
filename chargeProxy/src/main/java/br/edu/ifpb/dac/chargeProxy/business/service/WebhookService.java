package br.edu.ifpb.dac.chargeProxy.business.service;

import br.edu.ifpb.dac.chargeProxy.business.dto.AsaasWebhookDto;

/**
 * Service para processar webhooks recebidos do Asaas
 */
public interface WebhookService {

    /**
     * Processa o webhook recebido do Asaas e propaga a atualização para o Manager
     * 
     * @param webhookDto Dados do webhook
     */
    void processAsaasWebhook(AsaasWebhookDto webhookDto);
}
