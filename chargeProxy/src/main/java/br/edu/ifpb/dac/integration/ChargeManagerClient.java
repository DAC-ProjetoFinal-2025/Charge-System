package br.edu.ifpb.dac.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.edu.ifpb.dac.dto.ChargeResponseDTO;
import br.edu.ifpb.dac.dto.ChargeManagerDTO;

@FeignClient(name = "charge-manager", url = "${CHARGE_MANAGER_URL}")
public interface ChargeManagerClient {
    @PostMapping(value="/internal/charges", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ChargeResponseDTO notifyManager(@RequestBody ChargeManagerDTO chargeDTO);
}
