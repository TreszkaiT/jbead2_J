package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.PhoneDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.PhoneRepository;
import com.tt.jbead.services.impl.PhoneServiceImpl;
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
@RequestMapping(path = "/phone")
public class PhoneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//PhoneController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final PhoneServiceImpl phoneService;

    public PhoneController(PhoneServiceImpl phoneService,
                           PhoneRepository phoneRepository) {
        this.phoneService = phoneService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PhoneDTO>> findAll() {return ResponseEntity.ok(phoneService.findAll());}               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a PhoneDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PhoneDTO> create(@RequestBody @Valid PhoneDTO phoneDTO, BindingResult bindingResult) {     // @Valid a PhoneDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        PhoneDTO savePhone = phoneService.create(phoneDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savePhone);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PhoneDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<PhoneDTO> optionalPhoneDTO = phoneService.findById(identifier);

        ResponseEntity<PhoneDTO> response;
        if(optionalPhoneDTO.isPresent()){
            response = ResponseEntity.ok(optionalPhoneDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(null);
        }

        return response;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<PhoneDTO> update(@RequestBody @Valid PhoneDTO phoneDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        PhoneDTO updatePhone = phoneService.update(phoneDTO);
        return ResponseEntity.ok(updatePhone);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        phoneService.delete(id);
        return ResponseEntity.noContent().build();                                              // 204-es HTTP status code
    }

    private void checkErrors(BindingResult bindingResult){
        LOGGER.info("bindingResult has errors = {}", bindingResult.hasErrors());                // a stringet kiírja {}-ebbe meg behettesíti ezt bindingResult.hasErrors() hogy van-e hiba
        LOGGER.info("errors = {}", bindingResult.getAllErrors());                               // kiírjuk az összes hibát, amit csak talál   a hibaválaszt a response package-be tesszük bele

        if(bindingResult.hasErrors()){
            List<String> messages = new ArrayList<>();

            for(FieldError fieldError : bindingResult.getFieldErrors()){
                messages.add(fieldError.getField() + " - "+ fieldError.getDefaultMessage());
            }
            throw new InvalidEntityException("Invalid phone", messages);
        }
    }
}
