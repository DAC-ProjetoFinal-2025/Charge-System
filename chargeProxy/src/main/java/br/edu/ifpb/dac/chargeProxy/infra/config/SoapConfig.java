package br.edu.ifpb.dac.chargeProxy.infra.config;

import br.edu.ifpb.dac.chargeProxy.business.service.impl.ChargeServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapConfig {

    @Autowired
    private ChargeServiceImpl chargeService;

    @Bean
    public Endpoint endpoint() {
        // Criar o endpoint manualmente para configurar o Executor
        Endpoint endpoint = Endpoint.create(chargeService);

        // Configurar um pool de threads que herda o ClassLoader da aplicação
        // Isso resolve o ClassNotFoundException nas threads de processamento
        endpoint.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setContextClassLoader(this.getClass().getClassLoader());
            return t;
        }));

        endpoint.publish("http://0.0.0.0:8089/ws/charge");
        return endpoint;
    }
}
