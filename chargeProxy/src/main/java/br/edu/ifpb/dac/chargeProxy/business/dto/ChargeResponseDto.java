package br.edu.ifpb.dac.chargeProxy.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ChargeResponseDto {

    private String id;
    private String status;

    @JsonProperty("invoiceUrl")
    private String paymentUrl;

}
