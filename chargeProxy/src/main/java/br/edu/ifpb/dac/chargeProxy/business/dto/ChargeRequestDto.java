package br.edu.ifpb.dac.chargeProxy.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ChargeRequestDto implements Serializable {

    @JsonProperty("value")
    private BigDecimal amount;

    @JsonProperty("billingType")
    private String paymentType;

    private String customer;
    private String dueDate;

}
