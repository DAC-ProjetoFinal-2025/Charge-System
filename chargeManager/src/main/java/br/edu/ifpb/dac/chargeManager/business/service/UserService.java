package br.edu.ifpb.dac.chargeManager.business.service;

import br.edu.ifpb.dac.chargeManager.business.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();
}
