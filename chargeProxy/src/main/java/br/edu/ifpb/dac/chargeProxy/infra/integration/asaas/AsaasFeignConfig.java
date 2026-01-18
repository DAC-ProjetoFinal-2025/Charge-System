package br.edu.ifpb.dac.chargeProxy.infra.integration.asaas;

import br.edu.ifpb.dac.chargeProxy.infra.config.AsaasProperties;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public class AsaasFeignConfig {

    private final AsaasProperties asaasProperties;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // HARDCODE PARA TESTE FINAL - COLOQUE SUA CHAVE AQUI
            String key = "$aact_hmlg_000MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OmYyM2FjOGQyLWZlMGYtNGM2Ni04NzVmLWI3ZDQzODMxNjc0Yjo6JGFhY2hfMzNmMjkzMDItMWRiNy00Yjc3LTkyOTYtMTNhZDUyODJkNzZl";

            if (key != null) {
                // Mantemos a limpeza para garantir que não haja lixo binário no CTRL+C / CTRL+V
                key = key.replaceAll("[^\\x20-\\x7E]", "").trim();

                log.info("### INTERCEPTOR ASAAS (HARDCODED) ###");
                log.info("Key sanitized starts with: {}", key.substring(0, Math.min(key.length(), 4)));
            }

            requestTemplate.header("access-token", key);
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
