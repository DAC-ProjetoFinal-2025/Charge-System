package br.edu.ifpb.dac.chargeManager.infra.repository;

import br.edu.ifpb.dac.chargeManager.business.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Long id);
}
