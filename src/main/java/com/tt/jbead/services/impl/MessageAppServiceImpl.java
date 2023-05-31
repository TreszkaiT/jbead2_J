package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.MessageApp;
import com.tt.jbead.domain.dtos.MessageAppDTO;
import com.tt.jbead.exceptions.NotFoundExceptionMessageApp;
import com.tt.jbead.repositories.MessageAppRepository;
import com.tt.jbead.services.MessageAppService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageAppServiceImpl implements MessageAppService {

    private final MessageAppRepository messageAppRepository;
    private final ModelMapper modelMapper;

    public MessageAppServiceImpl(MessageAppRepository messageAppRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.messageAppRepository = messageAppRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MessageAppDTO> findAll() {
        List<MessageApp> messageAppList = messageAppRepository.findAll();

        return messageAppList.stream()
                .map(messageApp -> modelMapper.map(messageApp, MessageAppDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageAppDTO> findById(Integer id) {
        Optional<MessageApp> optionalMessageApp = messageAppRepository.findById(id);
        return optionalMessageApp.map(messageApp -> modelMapper.map(messageApp, MessageAppDTO.class));
    }

    @Override
    public MessageAppDTO create(MessageAppDTO messageAppDTO) {
        MessageApp messageAppToSave = modelMapper.map(messageAppDTO, MessageApp.class);
        messageAppToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        MessageApp savedMessageApp = messageAppRepository.save(messageAppToSave);
        return modelMapper.map(savedMessageApp, MessageAppDTO.class);
    }

    @Override
    public MessageAppDTO update(MessageAppDTO messageAppDTO) {
        Integer id = messageAppDTO.getId();
        Optional<MessageApp> optionalMessageApp = messageAppRepository.findById(id);

        if(optionalMessageApp.isEmpty()){
            throw new NotFoundExceptionMessageApp("MessageApp not found in database with id="+id);
        }

        MessageApp messageAppToUpdate = modelMapper.map(messageAppDTO, MessageApp.class);
        MessageApp saveMessageApp = messageAppRepository.save(messageAppToUpdate);
        return modelMapper.map(saveMessageApp, messageAppDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<MessageApp> optionalMessageApp = messageAppRepository.findById(id);

        if(optionalMessageApp.isPresent()){
            MessageApp messageAppToDelete = optionalMessageApp.get();
            messageAppRepository.delete(messageAppToDelete);
        } else {
            throw new NotFoundExceptionMessageApp("MessageApp not found in database with id="+id);
        }
    }
}