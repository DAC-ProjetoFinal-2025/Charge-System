package br.edu.ifpb.dac.chargeManager.infra.client;

import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeRequestDto;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeResponseDto;
import br.edu.ifpb.dac.chargeManager.infra.client.soap.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChargeProxyClient {

    @Autowired
    private ChargeService chargeSoapClient;

    public ChargeResponseDto sendCharge(BigDecimal amount, String paymentType, String customer, String dueDate) {
        ChargeRequestDto request = new ChargeRequestDto();
        request.setAmount(amount);
        request.setPaymentType(paymentType);
        request.setCustomer(customer);
        request.setDueDate(dueDate);

        // Calling the SOAP service
        return chargeSoapClient.charge(request);
    }

    public boolean cancelCharge(String externalId) {
        return chargeSoapClient.cancel(externalId);
    }
}
