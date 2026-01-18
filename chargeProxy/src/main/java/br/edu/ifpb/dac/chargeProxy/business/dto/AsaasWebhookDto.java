package br.edu.ifpb.dac.chargeProxy.business.dto;

import lombok.Data;

/**
 * DTO para receber o payload do webhook do Asaas
 * Documentação: https://docs.asaas.com/reference/webhooks
 */
@Data
public class AsaasWebhookDto {

    private String event; // Tipo do evento: PAYMENT_RECEIVED, PAYMENT_CONFIRMED, etc.
    private Payment payment; // Dados do pagamento

    @Data
    public static class Payment {
        private String id; // ID do pagamento no Asaas
        private String status; // Status: PENDING, RECEIVED, CONFIRMED, etc.
        private String customer; // ID do cliente
        private Double value; // Valor do pagamento
        private String billingType; // Tipo de cobrança
        private String dueDate; // Data de vencimento
        private String paymentDate; // Data do pagamento (se pago)
    }
}
