package br.edu.ifpb.dac.chargeSystem.chargeProxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ChargeProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargeProxyApplication.class, args);
	}

}
