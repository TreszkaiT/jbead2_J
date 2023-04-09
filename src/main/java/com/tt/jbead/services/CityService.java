package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.CityDTO;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<CityDTO> findAll();

    Optional<CityDTO> findById(Integer id);

    CityDTO create(CityDTO cityDTO);

    CityDTO update(CityDTO cityDTO);

    void delete(Integer id);
}
