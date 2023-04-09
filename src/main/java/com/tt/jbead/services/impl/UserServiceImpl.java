package com.tt.jbead.services.impl;

import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.domain.entities.User;
import com.tt.jbead.login.repositories.UserRepository;
import com.tt.jbead.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> FindUserByEmail(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail((userDTO.getEmail()));
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> findById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(optionalUser, UserDTO.class));
    }

    @Override
    public UserDTO CreateUser(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if(!optionalUser.isEmpty()){
            return null;
        }
        User userToSave = modelMapper.map(userDTO, User.class);
        userToSave.setId(null);
        User savedUser = userRepository.save(userToSave);
        return modelMapper.map(userToSave, UserDTO.class);
    }
}
