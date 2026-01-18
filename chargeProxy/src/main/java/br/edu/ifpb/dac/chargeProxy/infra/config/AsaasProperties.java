package br.edu.ifpb.dac.chargeProxy.infra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "asaas")
@Getter
@Setter
public class AsaasProperties {
    private String url;
    private String key;
}
