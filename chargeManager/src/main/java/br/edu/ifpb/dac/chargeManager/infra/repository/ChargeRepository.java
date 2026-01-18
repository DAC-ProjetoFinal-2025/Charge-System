package br.edu.ifpb.dac.chargeManager.infra.repository;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository {

    Charge save(Charge charge);

    Optional<Charge> findById(Long id);

    Optional<Charge> findByExternalId(String externalId);

    List<Charge> findByUserId(Long userId);

    void update(Charge charge);
}
