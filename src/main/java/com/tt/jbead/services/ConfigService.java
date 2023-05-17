package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.ConfigDTO;

import java.util.Optional;

public interface ConfigService {

    Optional<ConfigDTO> findByUserId(Integer userId);

    Optional<ConfigDTO> updateByUserId(ConfigDTO configDTO);

//    CityDTO create(CityDTO cityDTO);
//
//    CityDTO update(CityDTO cityDTO);
//
//    void delete(Integer id);
}
