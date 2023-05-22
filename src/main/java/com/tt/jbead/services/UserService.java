package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

public interface UserService {

    Optional<UserDTO> FindUserByEmail(UserDTO userDTO);

    Optional<UserDTO> findById(Integer id);

    List<UserDTO> findAll();

    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO personDTO);

    void delete(Integer id);
}
