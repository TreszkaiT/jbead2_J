package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.OtherSkillDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.OtherSkillRepository;
import com.tt.jbead.services.impl.OtherSkillServiceImpl;
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
@RequestMapping(path = "/otherSkill")
public class OtherSkillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//OtherSkillController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final OtherSkillServiceImpl otherSkillService;

    public OtherSkillController(OtherSkillServiceImpl otherSkillService,
                                OtherSkillRepository otherSkillRepository) {
        this.otherSkillService = otherSkillService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<OtherSkillDTO>> findAll() {return ResponseEntity.ok(otherSkillService.findAll());}               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a OtherSkillDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<OtherSkillDTO> create(@RequestBody @Valid OtherSkillDTO otherSkillDTO, BindingResult bindingResult) {     // @Valid a MovieDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        OtherSkillDTO saveOtherSkill = otherSkillService.create(otherSkillDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(saveOtherSkill);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<OtherSkillDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<OtherSkillDTO> optionalOtherSkillDTO = otherSkillService.findById(identifier);

        ResponseEntity<OtherSkillDTO> response;
        if(optionalOtherSkillDTO.isPresent()){
            response = ResponseEntity.ok(optionalOtherSkillDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(null);
        }

        return response;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<OtherSkillDTO> update(@RequestBody @Valid OtherSkillDTO otherSkillDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        OtherSkillDTO updateOtherSkill = otherSkillService.update(otherSkillDTO);
        return ResponseEntity.ok(updateOtherSkill);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        otherSkillService.delete(id);
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
            throw new InvalidEntityException("Invalid otherSkill", messages);
        }
    }
}
