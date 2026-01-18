package br.edu.ifpb.dac.chargeManager.infra.config;

import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeServiceImplService;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeService;
import jakarta.xml.ws.BindingProvider;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Value("${spring.charge-proxy.wsdl-url}")
    private String wsdlUrl;

    @Bean
    public ChargeService chargeSoapClient() {

        try {
            ChargeServiceImplService service = new ChargeServiceImplService(new URL(wsdlUrl));

            ChargeService port = service.getChargeServiceImplPort();

            BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlUrl.replace("?wsdl", ""));

            return port;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro na URL do serviço SOAP: " + wsdlUrl, e);
        }

    }
}
