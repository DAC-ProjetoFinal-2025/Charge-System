package br.edu.ifpb.dac.chargeManager.infra.config;

import br.edu.ifpb.dac.chargeManager.business.service.impl.WebhookServiceImpl;
import jakarta.xml.ws.Endpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para publicar o endpoint SOAP de webhook
 * Este endpoint será chamado pelo ChargeProxy quando receber webhooks do Asaas
 */
@Slf4j
@Configuration
public class WebhookSoapConfig {

    @Autowired
    private WebhookServiceImpl webhookService;

    @Bean
    public Endpoint webhookEndpoint() {
        String address = "http://0.0.0.0:8090/ws/webhook";
        log.info("Publicando endpoint SOAP de webhook em: {}", address);
        Endpoint endpoint = Endpoint.publish(address, webhookService);
        log.info("Endpoint SOAP de webhook publicado com sucesso!");
        return endpoint;
    }
}
