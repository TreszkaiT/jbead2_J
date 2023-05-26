package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.SocialMediaDTO;
import com.tt.jbead.domain.dtos.StudyDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.SocialMediaRepository;
import com.tt.jbead.services.impl.SocialMediaServiceImpl;
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
@RequestMapping(path = "/socialmedia")
public class SocialMediaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//SocialMediaController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final SocialMediaServiceImpl socialMediaService;

    public SocialMediaController(SocialMediaServiceImpl socialMediaService,
                                 SocialMediaRepository socialMediaRepository) {
        this.socialMediaService = socialMediaService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SocialMediaDTO>> findAll() {               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a SocialMediaDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;
//        return ResponseEntity.ok(socialMediaService.findAll());
        List<SocialMediaDTO> socialMediaDTOS = socialMediaService.findAll();

        return ResponseEntity.ok().body(socialMediaDTOS);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<SocialMediaDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<SocialMediaDTO> optionalSocialMediaDTO = socialMediaService.findById(identifier);

        ResponseEntity<SocialMediaDTO> response;
        if(optionalSocialMediaDTO.isPresent()){
            response = ResponseEntity.ok(optionalSocialMediaDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SocialMediaDTO> create(@RequestBody @Valid SocialMediaDTO socialMediaDTO, BindingResult bindingResult) {     // @Valid a ProofExperienceDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        SocialMediaDTO savedSocialMedia = socialMediaService.create(socialMediaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedSocialMedia);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<SocialMediaDTO> update(@RequestBody @Valid SocialMediaDTO socialMediaDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        SocialMediaDTO updatedSocialMedia = socialMediaService.update(socialMediaDTO);
        return ResponseEntity.ok(updatedSocialMedia);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        socialMediaService.delete(id);
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
            throw new InvalidEntityException("Invalid socialMedia", messages);
        }
    }
}
