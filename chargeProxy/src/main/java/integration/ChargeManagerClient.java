package integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import dto.ChargeResponseDTO;
import dto.ChargeManagerDTO;

@FeignClient(name = "charge-manager", url = "${charge-manager.url}")
public interface ChargeManagerClient {
    @PostMapping("/internal/charges")
    ChargeResponseDTO notifyManager(@RequestBody ChargeManagerDTO chargeDTO);
}
