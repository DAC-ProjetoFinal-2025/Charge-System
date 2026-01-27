package br.edu.ifpb.dac.chargeProxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class ChargeProxyApplication {

	static {
		// Forçar a implementação do JAXB para evitar ClassNotFoundException em tempo de
		// execução
		System.setProperty("jakarta.xml.bind.JAXBContextFactory", "org.glassfish.jaxb.runtime.v2.ContextFactory");
	}

	public static void main(String[] args) {
		var context = SpringApplication.run(ChargeProxyApplication.class, args);
		String asaasUrl = context.getEnvironment().getProperty("asaas.url");
		String asaasKey = context.getEnvironment().getProperty("asaas.key");
		String asaasWebhookToken = context.getEnvironment().getProperty("asaas.webhook.token");

		log.info("################################################");
		log.info("#### PROXY VERSÃO 1.9 - HARDCODED ####");
		log.info("#### URL: {} ####", asaasUrl);
		log.info("#### API KEY: {} ####",
				(asaasKey != null && !asaasKey.trim().isEmpty() && !asaasKey.contains("${"))
						? "CARREGADA (Mascarada: " + asaasKey.substring(0, Math.min(asaasKey.length(), 4)) + "...)"
						: "NÃO CARREGADA (VERIFIQUE OS SECRETS!)");
		log.info("#### WEBHOOK TOKEN: {} ####",
				(asaasWebhookToken != null && !asaasWebhookToken.trim().isEmpty() && !asaasWebhookToken.contains("${"))
						? "CARREGADA (Mascarada: "
								+ asaasWebhookToken.substring(0, Math.min(asaasWebhookToken.length(), 4)) + "...)"
						: "NÃO CARREGADA (VERIFIQUE OS SECRETS!)");
		log.info("################################################");
	}

}
