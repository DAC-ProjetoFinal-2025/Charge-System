package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;

import java.util.List;

public interface ChargeService {

    Charge createCharge(Charge charge);

    Charge getChargeById(Long id);

    List<Charge> getChargesByUserId(Long userId);

    List<Charge> getAllCharges();
}
