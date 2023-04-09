package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.LanguageDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.LanguageRepository;
import com.tt.jbead.services.impl.LanguageServiceImpl;
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
@RequestMapping(path = "/language")
public class LanguageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//LanguageController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final LanguageServiceImpl languageService;

    public LanguageController(LanguageServiceImpl languageService) {
        this.languageService = languageService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<LanguageDTO>> findAll() {return ResponseEntity.ok(languageService.findAll());}               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a LanguageDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<LanguageDTO> create(@RequestBody @Valid LanguageDTO languageDTO, BindingResult bindingResult) {     // @Valid a MovieDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        LanguageDTO saveLanguage = languageService.create(languageDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(saveLanguage);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<LanguageDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<LanguageDTO> optionalLanguageDTO = languageService.findById(identifier);

        ResponseEntity<LanguageDTO> response;
        if(optionalLanguageDTO.isPresent()){
            response = ResponseEntity.ok(optionalLanguageDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(null);
        }

        return response;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<LanguageDTO> update(@RequestBody @Valid LanguageDTO languageDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        LanguageDTO updateLanguage = languageService.update(languageDTO);
        return ResponseEntity.ok(updateLanguage);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        languageService.delete(id);
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
            throw new InvalidEntityException("Invalid language", messages);
        }
    }
}
