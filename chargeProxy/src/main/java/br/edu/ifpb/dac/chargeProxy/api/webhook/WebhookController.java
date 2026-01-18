package br.edu.ifpb.dac.chargeProxy.api.webhook;

import br.edu.ifpb.dac.chargeProxy.business.dto.AsaasWebhookDto;
import br.edu.ifpb.dac.chargeProxy.business.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller para receber webhooks do Asaas

@Slf4j
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @Value("${ASAAS_WEBHOOK_TOKEN:}")
    private String webhookToken;

    @PostMapping("/asaas")
    public ResponseEntity<String> receiveAsaasWebhook(
            @RequestHeader(value = "asaas-access-token", required = false) String token,
            @RequestBody AsaasWebhookDto webhookDto) {

        // Validar token do webhook
        if (!isValidToken(token)) {
            log.warn("Tentativa de acesso ao webhook com token inválido. Token recebido: {}",
                    token != null ? "***" : "null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de autenticação inválido");
        }

        log.info("Webhook recebido do Asaas - Event: {}", webhookDto.getEvent());

        try {
            // Processa o webhook
            webhookService.processAsaasWebhook(webhookDto);

            log.info("Webhook processado com sucesso");
            return ResponseEntity.ok("Webhook processado com sucesso");

        } catch (Exception e) {
            log.error("Erro ao processar webhook", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar webhook: " + e.getMessage());
        }
    }

    /**
     * Endpoint de teste para verificar se o webhook está acessível
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Webhook endpoint está funcionando!");
    }

    /**
     * Valida o token recebido no header do webhook
     */
    private boolean isValidToken(String token) {
        if (token == null || token.isBlank()) {
            log.warn("Token do webhook está vazio ou nulo");
            return false;
        }

        if (webhookToken == null || webhookToken.isBlank()) {
            log.error("ASAAS_WEBHOOK_TOKEN não configurado no servidor!");
            return false;
        }

        boolean isValid = token.equals(webhookToken);
        if (!isValid) {
            log.warn("Token do webhook não corresponde ao token configurado");
        }

        return isValid;
    }
}
