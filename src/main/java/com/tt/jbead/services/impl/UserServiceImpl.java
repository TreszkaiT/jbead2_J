package com.tt.jbead.services.impl;

import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.domain.entities.User;
import com.tt.jbead.exceptions.notfound.UserNotFoundException;
import com.tt.jbead.repositories.UserRepository;
import com.tt.jbead.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        //return Optional.of(modelMapper.map(optionalUser, UserDTO.class));
        return optionalUser.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public List<UserDTO> findAll(){
//        List<User> userList = userRepository.findAll();
//
//        return userList.stream()
//                .map(user -> modelMapper.map(user, UserDTO.class))
//                .collect(Collectors.toList());

        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if(!optionalUser.isEmpty()){
            return null;
        }
        User userToSave = modelMapper.map(userDTO, User.class);
        userToSave.setId(null);
        User savedUser = userRepository.save(userToSave);
        return modelMapper.map(userToSave, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Integer id = userDTO.getId();
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found in database with id= "+id);
        }

        User userToUpdate = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(userToUpdate);
        return modelMapper.map(savedUser, userDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found in database with id= "+id);
        }

        User userToDelete = optionalUser.get();
        userRepository.delete(userToDelete);
    }


}
