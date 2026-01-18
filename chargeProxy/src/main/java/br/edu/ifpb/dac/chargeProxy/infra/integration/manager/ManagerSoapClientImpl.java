package br.edu.ifpb.dac.chargeProxy.infra.integration.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.URL;

/**
 * Implementação do cliente SOAP para o Manager
 * Usa JAX-WS para gerar o cliente dinamicamente a partir do WSDL
 */
@Slf4j
@Component
public class ManagerSoapClientImpl implements ManagerSoapClient {

    @Value("${manager.wsdl.url}")
    private String wsdlUrl;

    @Override
    public boolean updateChargeStatus(String externalId, String status) {
        try {
            log.info("Conectando ao Manager SOAP em: {}", wsdlUrl);

            // Cria o serviço SOAP dinamicamente
            URL url = new URL(wsdlUrl);
            QName qname = new QName("http://impl.service.business.chargeManager.dac.ifpb.edu.br/",
                    "WebhookServiceImplService");
            Service service = Service.create(url, qname);

            // Obtém o port do serviço
            QName portQName = new QName("http://impl.service.business.chargeManager.dac.ifpb.edu.br/",
                    "WebhookServiceImplPort");

            // Cria um proxy dinâmico para chamar o método
            ManagerWebhookPort port = service.getPort(portQName, ManagerWebhookPort.class);

            // Chama o método SOAP
            UpdateStatusResponse response = port.updateChargeStatus(externalId, status);

            log.info("Resposta do Manager: {}", response.getMessage());
            return response.isSuccess();

        } catch (Exception e) {
            log.error("Erro ao chamar Manager via SOAP", e);
            return false;
        }
    }

    // Interface para o port SOAP (será gerada pelo WSDL)
    public interface ManagerWebhookPort {
        UpdateStatusResponse updateChargeStatus(String externalId, String status);
    }

    // DTO para a resposta
    public static class UpdateStatusResponse {
        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
