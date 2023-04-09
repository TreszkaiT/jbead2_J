package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.PhoneDTO;

import java.util.List;
import java.util.Optional;

public interface PhoneService {

    List<PhoneDTO> findAll();

    Optional<PhoneDTO> findById(Integer id);

    PhoneDTO create(PhoneDTO phoneDTO);

    PhoneDTO update(PhoneDTO phoneDTO);

    void delete(Integer id);
}
