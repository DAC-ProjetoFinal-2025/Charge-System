package br.edu.ifpb.dac.controller;

import br.edu.ifpb.dac.dto.ChargeDTO;
import br.edu.ifpb.dac.dto.ChargeResponseDTO;
import br.edu.ifpb.dac.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/charges")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChargeResponseDTO> receiveCharge(@RequestBody ChargeDTO chargeDTO) {
        System.out.println("Chegou no manager Controlloer ");
        // Converte para ChargeDTO se necessário (caso venha sem id)
        ChargeDTO chargeToProcess = new ChargeDTO();
        chargeToProcess.setAmount(chargeDTO.getAmount());
        chargeToProcess.setCustomerName(chargeDTO.getCustomerName());
        chargeToProcess.setStatus(chargeDTO.getStatus() != null ? chargeDTO.getStatus() : "PENDING");
        
        ChargeDTO processed = managerService.processCharge(chargeToProcess);
        
        ChargeResponseDTO response = new ChargeResponseDTO();
        response.setId(processed.getId() != null ? processed.getId().toString() : null);
        response.setStatus(processed.getStatus());
        response.setMessage("Cobrança processada com sucesso");
        
        return ResponseEntity.ok(response);
    }
}