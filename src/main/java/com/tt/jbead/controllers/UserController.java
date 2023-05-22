package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.CityDTO;
import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        UserDTO createdUserDTO = userService.create(userDTO);

        ResponseEntity<UserDTO> response;
        if(createdUserDTO.equals(null)){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            response = ResponseEntity.ok(createdUserDTO);
        }
        return response;
    }


    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")                          // , produces = "applications/json"
    public ResponseEntity<List<UserDTO>> findAll() {
        //System.out.println("getUser");return ResponseEntity.ok(userService.findAll());
        List<UserDTO> users = userService.findAll();

        return ResponseEntity.ok().body(users);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") Integer identifier){
        Optional<UserDTO> optionalUserDTO = userService.findById(identifier);

        ResponseEntity<UserDTO> response;
        if(optionalUserDTO.isPresent()){
            response = ResponseEntity.ok(optionalUserDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        checkErrors(bindingResult);

        UserDTO updateUser = userService.create(userDTO);
        return ResponseEntity.ok(updateUser);
    }

    // frissítés id alapján
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){                     // @RequestBody MovieDTO userDTO:  a Request Body-ban várja az infót
        checkErrors(bindingResult);                                                             // alul alapmetódust írunk a validációs hibák lekezelésére

        UserDTO updatedUser = userService.update(userDTO);
        return ResponseEntity.ok(updatedUser);                                                 // 200-as hiba
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void checkErrors(BindingResult bindingResult){
        LOGGER.info("bindigResult has errors = {}", bindingResult.hasErrors());
        LOGGER.info("erors = {}", bindingResult.getAllErrors());

        if(bindingResult.hasErrors()){
            List<String> messages = new ArrayList<>();

            for(FieldError fieldError : bindingResult.getFieldErrors()){
                messages.add(fieldError.getField() + " - " + fieldError.getDefaultMessage());
            }

            throw new InvalidEntityException("Invalid user", messages);
        }
    }
}
