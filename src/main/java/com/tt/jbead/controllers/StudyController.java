package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.StudyDTO;
import com.tt.jbead.domain.dtos.UserDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.StudyRepository;
import com.tt.jbead.services.impl.StudyServiceImpl;
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
@RequestMapping(path = "/study")
public class StudyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//StudyController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final StudyServiceImpl studyService;

    public StudyController(StudyServiceImpl studyService,
                           StudyRepository studyRepository) {
        this.studyService = studyService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<StudyDTO>> findAll() {               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a StudyDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;
//        return ResponseEntity.ok(studyService.findAll());
        List<StudyDTO> studies = studyService.findAll();

        return ResponseEntity.ok().body(studies);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<StudyDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<StudyDTO> optionalStudyDTO = studyService.findById(identifier);

        ResponseEntity<StudyDTO> response;
        if(optionalStudyDTO.isPresent()){
            response = ResponseEntity.ok(optionalStudyDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<StudyDTO> create(@RequestBody @Valid StudyDTO studyDTO, BindingResult bindingResult) {     // @Valid a ProofExperienceDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        StudyDTO createdStudy = studyService.create(studyDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdStudy);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<StudyDTO> update(@RequestBody @Valid StudyDTO studyDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        StudyDTO updateStudy = studyService.update(studyDTO);
        return ResponseEntity.ok(updateStudy);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        studyService.delete(id);
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
            throw new InvalidEntityException("Invalid study", messages);
        }
    }
}
