package br.edu.ifpb.dac.chargeManager.api.controller;

import br.edu.ifpb.dac.chargeManager.api.dto.CreateChargeRequestDto;
import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import br.edu.ifpb.dac.chargeManager.business.service.ChargeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChargeController.class)
public class ChargeRouteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChargeService chargeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldFindChargeRoute() throws Exception {
        // Mock the service to return a dummy charge so the controller can proceed
        Charge dummyCharge = Charge.builder()
                .id(1L)
                .userId(1L)
                .name("Test Charge")
                .amount(new BigDecimal("100.00"))
                .paymentType("CREDIT_CARD")
                .status("PENDING")
                .build();

        Mockito.when(chargeService.createCharge(any(Charge.class))).thenReturn(dummyCharge);

        CreateChargeRequestDto request = CreateChargeRequestDto.builder()
                .userId(1L)
                .name("Test Charge")
                .amount(new BigDecimal("100.00"))
                .paymentType("CREDIT_CARD")
                .build();

        mockMvc.perform(post("/api/charges")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
