package controller;

import dto.ChargeDTO;
import dto.ChargeResponseDTO;
import service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/charges")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ChargeResponseDTO> receiveCharge(@RequestBody ChargeDTO chargeDTO) {
        ChargeDTO processed = managerService.processCharge(chargeDTO);
        
        ChargeResponseDTO response = new ChargeResponseDTO();
        response.setId(processed.getId() != null ? processed.getId().toString() : null);
        response.setStatus(processed.getStatus());
        response.setMessage("Cobrança processada com sucesso");
        
        return ResponseEntity.ok(response);
    }
}