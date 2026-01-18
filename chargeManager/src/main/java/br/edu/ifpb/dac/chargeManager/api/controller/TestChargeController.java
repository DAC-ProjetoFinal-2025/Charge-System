package br.edu.ifpb.dac.chargeManager.api.controller;

import br.edu.ifpb.dac.chargeManager.infra.client.ChargeProxyClient;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-charge")
public class TestChargeController {

    @Autowired
    private ChargeProxyClient chargeProxyClient;

    @GetMapping
    public ChargeResponseDto testCharge(
            @RequestParam(defaultValue = "100.00") String amount,
            @RequestParam(defaultValue = "PIX") String paymentType,
            @RequestParam(defaultValue = "cus_000005031717") String customer,
            @RequestParam(defaultValue = "2026-12-31") String dueDate) {
        System.out.println("Solicitando cobrança via Manager para o Proxy...");
        return chargeProxyClient.sendCharge(new java.math.BigDecimal(amount), paymentType, customer, dueDate);
    }
}
