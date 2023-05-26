package com.tt.jbead.controllers;

import com.tt.jbead.domain.dtos.MessageAppDTO;
import com.tt.jbead.domain.dtos.OtherSkillDTO;
import com.tt.jbead.exceptions.InvalidEntityException;
import com.tt.jbead.repositories.MessageAppRepository;
import com.tt.jbead.services.impl.MessageAppServiceImpl;
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
@RequestMapping(path = "/messageapp")
public class MessageAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassName.class);//MethodHandles.lookup().lookupClass());//MessageAppController.class);       // a this.getClass().getName() static metódusnál nem jó

    private final MessageAppServiceImpl messageAppService;

    public MessageAppController(MessageAppServiceImpl messageAppService,
                                MessageAppRepository messageAppRepository) {
        this.messageAppService = messageAppService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<MessageAppDTO>> findAll() {               // ResponseEntity : a HTTP válaszon tudunk módosítani vele. 200,201... úgy hogy a MessageAppDTO-t becsomagoljuk ebbe a ResponseEntity generikus osztályba; HTTP headereket is bele tudunk még e mellett pakolni   ;;;
//        return ResponseEntity.ok(messageAppService.findAll());
        List<MessageAppDTO> messageAppDTOS = messageAppService.findAll();

        return ResponseEntity.ok().body(messageAppDTOS);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MessageAppDTO> findById(@PathVariable(name = "id") Integer identifier) {
        Optional<MessageAppDTO> optionalMessageAppDTO = messageAppService.findById(identifier);

        ResponseEntity<MessageAppDTO> response;
        if(optionalMessageAppDTO.isPresent()){
            response = ResponseEntity.ok(optionalMessageAppDTO.get());
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<MessageAppDTO> create(@RequestBody @Valid MessageAppDTO messageAppDTO, BindingResult bindingResult) {     // @Valid a MessageAppDTO-ban használja így a validációt  ;;  , BindingResult bindingResult  a validációs hibákat ebbe teszi bele, és mi azokat le tudjuk innen kérni
        checkErrors(bindingResult);

        MessageAppDTO savedMessageApp = messageAppService.create(messageAppDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedMessageApp);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<MessageAppDTO> update(@RequestBody @Valid MessageAppDTO messageAppDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);

        MessageAppDTO updatedMessageApp = messageAppService.update(messageAppDTO);
        return ResponseEntity.ok(updatedMessageApp);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){                               // <Void>  : üres response Entity-t ad vissza, így ezen tudjuk állítgatni a HTTP status kódot
        messageAppService.delete(id);
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
            throw new InvalidEntityException("Invalid messageApp", messages);
        }
    }
}
