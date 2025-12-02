package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import dto.ChargeRequestDTO;
import dto.ChargeResponseDTO;
import service.ChargeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/charges")
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    @PostMapping
    public ChargeResponseDTO createCharge(@RequestBody ChargeRequestDTO request) {
        log.info("Creating charge: {}", request);
        return chargeService.createCharge(request);
    }
}