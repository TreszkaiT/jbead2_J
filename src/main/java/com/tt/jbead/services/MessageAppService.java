package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.MessageAppDTO;

import java.util.List;
import java.util.Optional;

public interface MessageAppService {

    List<MessageAppDTO> findAll();

    Optional<MessageAppDTO> findById(Integer id);

    MessageAppDTO create(MessageAppDTO messageAppDTO);

    MessageAppDTO update(MessageAppDTO messageAppDTO);

    void delete(Integer id);
}
