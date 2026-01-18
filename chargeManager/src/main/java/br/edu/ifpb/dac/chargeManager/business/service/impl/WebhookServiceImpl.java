package br.edu.ifpb.dac.chargeManager.business.service.impl;

import br.edu.ifpb.dac.chargeManager.api.dto.UpdateChargeStatusResponseDto;
import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import br.edu.ifpb.dac.chargeManager.business.service.WebhookService;
import br.edu.ifpb.dac.chargeManager.infra.repository.ChargeRepository;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação do serviço SOAP de webhook
 * Recebe atualizações de status do ChargeProxy e atualiza o banco de dados
 */
@Slf4j
@Service
@RequiredArgsConstructor
@WebService(endpointInterface = "br.edu.ifpb.dac.chargeManager.business.service.WebhookService")
public class WebhookServiceImpl implements WebhookService {

    private final ChargeRepository chargeRepository;

    @Override
    public UpdateChargeStatusResponseDto updateChargeStatus(String externalId, String status) {
        log.info("Recebendo atualização de status via webhook - ExternalId: {}, Status: {}",
                externalId, status);

        try {
            // Busca a cobrança pelo externalId (ID do Asaas)
            Optional<Charge> chargeOpt = chargeRepository.findByExternalId(externalId);

            if (chargeOpt.isEmpty()) {
                String errorMsg = "Cobrança não encontrada com externalId: " + externalId;
                log.warn(errorMsg);
                return new UpdateChargeStatusResponseDto(false, errorMsg);
            }

            Charge charge = chargeOpt.get();
            String oldStatus = charge.getStatus();

            // Atualiza o status
            charge.setStatus(status);
            chargeRepository.update(charge);

            log.info("Status atualizado com sucesso - ID: {}, Status anterior: {}, Novo status: {}",
                    charge.getId(), oldStatus, status);

            return new UpdateChargeStatusResponseDto(true,
                    "Status atualizado de " + oldStatus + " para " + status);

        } catch (Exception e) {
            String errorMsg = "Erro ao atualizar status: " + e.getMessage();
            log.error(errorMsg, e);
            return new UpdateChargeStatusResponseDto(false, errorMsg);
        }
    }
}
