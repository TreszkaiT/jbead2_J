package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> FindUserByEmail(UserDTO userDTO);

    Optional<UserDTO> findById(Integer id);

    List<UserDTO> findAll();

    UserDTO CreateUser(UserDTO userDTO);

    UserDTO update(UserDTO personDTO);

    void delete(Integer id);
}
