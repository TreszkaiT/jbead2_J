package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.ProofExperienceDTO;
import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.ProofExperienceRepository;
import com.tt.jbead.services.impl.ProofExperienceServiceImpl;
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
@RequestMapping(path = "/proofexperience")
public class ProofExperienceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//ProofExperienceController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final ProofExperienceServiceImpl proofExperienceService;

    public ProofExperienceController(ProofExperienceServiceImpl proofExperienceService,
                                     ProofExperienceRepository proofExperienceRepository) {
        this.proofExperienceService = proofExperienceService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ProofExperienceDTO>> findAll() {               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a ProofExperienceDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;
//        return ResponseEntity.ok(proofExperienceService.findAll());
        List<ProofExperienceDTO> proofExperienceDTOS = proofExperienceService.findAll();

        return ResponseEntity.ok().body(proofExperienceDTOS);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ProofExperienceDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<ProofExperienceDTO> optionalProofExperienceDTO = proofExperienceService.findById(identifier);

        ResponseEntity<ProofExperienceDTO> response;
        if(optionalProofExperienceDTO.isPresent()){
            response = ResponseEntity.ok(optionalProofExperienceDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ProofExperienceDTO> create(@RequestBody @Valid ProofExperienceDTO proofExperienceDTO, BindingResult bindingResult) {     // @Valid a ProofExperienceDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        ProofExperienceDTO savedProofExperience = proofExperienceService.create(proofExperienceDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedProofExperience);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<ProofExperienceDTO> update(@RequestBody @Valid ProofExperienceDTO proofExperienceDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        ProofExperienceDTO updatedProofExperience = proofExperienceService.update(proofExperienceDTO);
        return ResponseEntity.ok(updatedProofExperience);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        proofExperienceService.delete(id);
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
            throw new InvalidEntityException("Invalid proofExperience", messages);
        }
    }
}
