package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger((ClassName.class));

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<UserDTO> findByEmail(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        Optional<UserDTO> optionalUserDTO = userService.FindUserByEmail(userDTO);

        ResponseEntity<UserDTO> response;
        if(optionalUserDTO.isPresent()){
            response = ResponseEntity.ok(optionalUserDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return response;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        UserDTO createdUserDTO = userService.CreateUser(userDTO);

        ResponseEntity<UserDTO> response;
        if(createdUserDTO.equals(null)){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            response = ResponseEntity.ok(createdUserDTO);
        }
        return response;
    }
}
