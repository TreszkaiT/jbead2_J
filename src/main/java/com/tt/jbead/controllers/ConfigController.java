package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.ConfigDTO;
import com.tt.jbead.services.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/config")
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

//    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "applications/json")
//    public ResponseEntity<List<ConfigDTO>> findAll() { return ResponseEntity.ok(cityService.findAll());}

    @RequestMapping(path = "{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ConfigDTO> findById(@PathVariable(name = "id") Integer identifier){
        Optional<ConfigDTO> optionalConfigDTO = configService.findByUserId(identifier);

        ResponseEntity<ConfigDTO> response;
        if(optionalConfigDTO.isPresent()){
            response = ResponseEntity.ok(optionalConfigDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("kész");
        return response;
    }

//    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity<ConfigDTO> create(@RequestBody @Valid ConfigDTO ConfigDTO, BindingResult bindingResult){
//        checkErrors(bindingResult);
//
//        ConfigDTO updateCity = cityService.update(ConfigDTO);
//        return ResponseEntity.ok(updateCity);
//    }

//    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<Void> delete(@PathVariable Integer id){
//        cityService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    private void checkErrors(BindingResult bindingResult){
//        LOGGER.info("bindigResult has errors = {}", bindingResult.hasErrors());
//        LOGGER.info("erors = {}", bindingResult.getAllErrors());
//
//        if(bindingResult.hasErrors()){
//            List<String> messages = new ArrayList<>();
//
//            for(FieldError fieldError : bindingResult.getFieldErrors()){
//                messages.add(fieldError.getField() + " - " + fieldError.getDefaultMessage());
//            }
//
//            throw new InvalidEntityException("Invalid city", messages);
//        }
//    }
}
