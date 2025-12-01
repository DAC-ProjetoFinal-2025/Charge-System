package service;

import integration.ChargeManagerClient;

import org.springframework.stereotype.Service;
import dto.ChargeManagerDTO;
import dto.ChargeRequestDTO;
import dto.ChargeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargeService {
    private final ChargeManagerClient chargeManagerClient;

    public ChargeResponseDTO createCharge(ChargeRequestDTO request) {
        log.info("Processing charge request: {}", request);
        
        //Preparando DTO para enviar ao Manager
        ChargeManagerDTO managerDTO = new ChargeManagerDTO();
        managerDTO.setAmount(request.getAmount());
        managerDTO.setCustomerName(request.getCustomerName());
        managerDTO.setStatus("PENDING");

        ChargeResponseDTO response = chargeManagerClient.notifyManager(managerDTO);

        return response;
    }
}
