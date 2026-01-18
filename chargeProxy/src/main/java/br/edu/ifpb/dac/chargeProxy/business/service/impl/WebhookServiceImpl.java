package br.edu.ifpb.dac.chargeProxy.business.service.impl;

import br.edu.ifpb.dac.chargeProxy.business.dto.AsaasWebhookDto;
import br.edu.ifpb.dac.chargeProxy.business.service.WebhookService;
import br.edu.ifpb.dac.chargeProxy.infra.integration.manager.ManagerSoapClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço de processamento de webhooks
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final ManagerSoapClient managerSoapClient;

    @Override
    public void processAsaasWebhook(AsaasWebhookDto webhookDto) {
        log.info("Processando webhook do Asaas - Event: {}, Payment ID: {}",
                webhookDto.getEvent(),
                webhookDto.getPayment().getId());

        try {
            // Extrai os dados relevantes do webhook
            String externalId = webhookDto.getPayment().getId();
            String newStatus = webhookDto.getPayment().getStatus();

            log.info("Enviando atualização para o Manager - ExternalId: {}, Status: {}",
                    externalId, newStatus);

            // Chama o Manager via SOAP para atualizar o status
            boolean success = managerSoapClient.updateChargeStatus(externalId, newStatus);

            if (success) {
                log.info("Status atualizado com sucesso no Manager");
            } else {
                log.warn("Falha ao atualizar status no Manager");
            }

        } catch (Exception e) {
            log.error("Erro ao processar webhook do Asaas", e);
            throw new RuntimeException("Erro ao processar webhook", e);
        }
    }
}
