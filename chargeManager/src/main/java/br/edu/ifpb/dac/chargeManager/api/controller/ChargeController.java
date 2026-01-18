package br.edu.ifpb.dac.chargeManager.api.controller;

import br.edu.ifpb.dac.chargeManager.api.dto.ChargeResponseDto;
import br.edu.ifpb.dac.chargeManager.api.dto.CreateChargeRequestDto;
import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import br.edu.ifpb.dac.chargeManager.business.service.ChargeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/charges")
public class ChargeController {

    private final ChargeService chargeService;

    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping
    public ResponseEntity<ChargeResponseDto> createCharge(@Valid @RequestBody CreateChargeRequestDto requestDto) {
        Charge charge = Charge.builder()
                .userId(requestDto.getUserId())
                .name(requestDto.getName())
                .amount(requestDto.getAmount())
                .paymentType(requestDto.getPaymentType())
                .customer(requestDto.getCustomer())
                .dueDate(requestDto.getDueDate())
                .build();

        Charge createdCharge = chargeService.createCharge(charge);

        ChargeResponseDto responseDto = convertToDto(createdCharge);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeResponseDto> getChargeById(@PathVariable Long id) {
        Charge charge = chargeService.getChargeById(id);
        ChargeResponseDto responseDto = convertToDto(charge);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChargeResponseDto>> getChargesByUserId(@PathVariable Long userId) {
        List<Charge> charges = chargeService.getChargesByUserId(userId);

        List<ChargeResponseDto> responseDtos = charges.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    private ChargeResponseDto convertToDto(Charge charge) {
        return ChargeResponseDto.builder()
                .id(charge.getId())
                .userId(charge.getUserId())
                .name(charge.getName())
                .amount(charge.getAmount())
                .paymentType(charge.getPaymentType())
                .status(charge.getStatus())
                .externalId(charge.getExternalId())
                .createdAt(charge.getCreatedAt())
                .updatedAt(charge.getUpdatedAt())
                .build();
    }
}
