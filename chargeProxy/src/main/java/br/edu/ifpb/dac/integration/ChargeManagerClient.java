package br.edu.ifpb.dac.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.edu.ifpb.dac.dto.ChargeResponseDTO;
import br.edu.ifpb.dac.dto.ChargeManagerDTO;

@FeignClient(name = "charge-manager", url = "${CHARGE_MANAGER_URL}")
public interface ChargeManagerClient {
    @PostMapping("/internal/charges")
    ChargeResponseDTO notifyManager(@RequestBody ChargeManagerDTO chargeDTO);
}
