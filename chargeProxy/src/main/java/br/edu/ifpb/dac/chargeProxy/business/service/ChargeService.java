package br.edu.ifpb.dac.chargeProxy.business.service;

import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeRequestDto;
import br.edu.ifpb.dac.chargeProxy.business.dto.ChargeResponseDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ChargeService {

    @WebMethod
    ChargeResponseDto charge(ChargeRequestDto chargeRequestDto);

    @WebMethod
    boolean cancel(String externalId);
}