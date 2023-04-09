package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> FindUserByEmail(UserDTO userDTO);

    Optional<UserDTO> findById(Integer id);

    UserDTO CreateUser(UserDTO userDTO);
}
