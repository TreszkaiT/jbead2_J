package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.CityDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.services.impl.CityServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/city")
public class CityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);

    private final CityServiceImpl cityService;

    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")                      // produces = "applications/json", consumes = "applications/json" ... produces = MediaType.APPLICATION_JSON_VALUE
    public ResponseEntity<List<CityDTO>> findAll() {
        //System.out.println("getCity");return ResponseEntity.ok(cityService.findAll());
        List<CityDTO> cities = cityService.findAll();
        //System.out.println("getCityAll"+cities);
        return ResponseEntity.ok().body(cities);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CityDTO> findById(@PathVariable(name = "id") Integer identifier){
        Optional<CityDTO> optionalCityDTO = cityService.findById(identifier);

        ResponseEntity<CityDTO> response;
        if(optionalCityDTO.isPresent()){
            response = ResponseEntity.ok(optionalCityDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);              // return ResponseEntity.notFound().build();
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CityDTO> create(@RequestBody @Valid CityDTO cityDTO, BindingResult bindingResult){
        checkErrors(bindingResult);

        CityDTO updateCity = cityService.create(cityDTO);
        return ResponseEntity.ok(updateCity);
    }

    // frissítés id alapján
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<CityDTO> update(@RequestBody @Valid CityDTO cityDTO, BindingResult bindingResult){                     // @RequestBody CityDTO cityDTO:  a Request Body-ban várja az infót
        checkErrors(bindingResult);                                                             // alul alapmetódust írunk a validációs hibák lekezelésére

        CityDTO updatedCity = cityService.update(cityDTO);
        return ResponseEntity.ok(updatedCity);                                                 // 200-as hiba
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        cityService.delete(id);
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

            throw new InvalidEntityException("Invalid city", messages);
        }
    }
}
