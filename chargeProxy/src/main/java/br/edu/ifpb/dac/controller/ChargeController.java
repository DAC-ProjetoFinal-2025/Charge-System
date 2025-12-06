package br.edu.ifpb.dac.controller;


import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import br.edu.ifpb.dac.dto.ChargeRequestDTO;
import br.edu.ifpb.dac.dto.ChargeResponseDTO;
import br.edu.ifpb.dac.service.ChargeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/charges")
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    @PostMapping
    public ChargeResponseDTO createCharge(@RequestBody ChargeRequestDTO request) {
        System.out.println("Teste");
        log.info("Creating charge: {}", request);
        return chargeService.createCharge(request);
    }

    @GetMapping("/status")
    public String status() { return "ok"; }
}