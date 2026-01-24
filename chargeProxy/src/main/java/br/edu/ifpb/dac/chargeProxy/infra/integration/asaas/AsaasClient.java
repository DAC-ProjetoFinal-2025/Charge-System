package br.edu.ifpb.dac.chargeProxy.infra.integration.asaas;

import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeRequestDto;
import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "asaasClient", url = "${asaas.url}", configuration = AsaasFeignConfig.class)
public interface AsaasClient {

    @PostMapping("/api/v3/payments")
    ChargeResponseDto createCharge(@RequestBody ChargeRequestDto request);

    @org.springframework.web.bind.annotation.DeleteMapping("/api/v3/payments/{id}")
    void deleteCharge(@org.springframework.web.bind.annotation.PathVariable("id") String id);
}
