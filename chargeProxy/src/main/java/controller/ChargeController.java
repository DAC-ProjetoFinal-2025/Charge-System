package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import dto.ChargeRequestDTO;
import dto.ChargeResponseDTO;
import service.ChargeService;

@RestController
@RequestMapping("/charges")
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    @PostMapping
    public ChargeResponseDTO createCharge(@RequestBody ChargeRequestDTO request) {
        return chargeService.createCharge(request);
    }
}