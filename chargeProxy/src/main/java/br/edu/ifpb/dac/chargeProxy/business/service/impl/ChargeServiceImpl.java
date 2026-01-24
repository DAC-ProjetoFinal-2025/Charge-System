package br.edu.ifpb.dac.chargeProxy.business.service.impl;

import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeRequestDto;
import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeResponseDto;
import br.edu.ifpb.dac.chargeProxy.business.service.ChargeService;
import br.edu.ifpb.dac.chargeProxy.infra.integration.asaas.AsaasClient;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@WebService(endpointInterface = "br.edu.ifpb.dac.chargeProxy.business.service.ChargeService")
public class ChargeServiceImpl implements ChargeService {

    private final AsaasClient asaasClient;

    @Override
    public ChargeResponseDto charge(ChargeRequestDto chargeRequestDto) {
        try {
            System.out.println("### DEBUG: Iniciando chamada ao Asaas ###");

            // Logar os dados recebidos para conferência
            System.out.println("  - Cliente: " + chargeRequestDto.getCustomer());
            System.out.println("  - Valor (amout): " + chargeRequestDto.getAmount());
            System.out.println("  - Tipo (paymentType): " + chargeRequestDto.getPaymentType());
            System.out.println("  - Vencimento (dueDate): " + chargeRequestDto.getDueDate());

            // Converter para JSON para ver como o Jackson está mapeando os @JsonProperty
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String json = mapper.writeValueAsString(chargeRequestDto);
            System.out.println("  - PAYLOAD JSON: " + json);

            // Chama o Asaas via Feign Client
            return asaasClient.createCharge(chargeRequestDto);

        } catch (Exception e) {
            System.err.println("### ERRO NO PROXY: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cancel(String externalId) {
        try {
            System.out.println("### DEBUG: Cancelando cobrança no Asaas: " + externalId + " ###");
            asaasClient.deleteCharge(externalId);
            return true;
        } catch (Exception e) {
            System.err.println("### ERRO AO CANCELAR NO PROXY: " + e.getMessage());
            return false;
        }
    }
}
