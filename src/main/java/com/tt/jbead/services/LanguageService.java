package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.LanguageDTO;

import java.util.List;
import java.util.Optional;

public interface LanguageService {

    List<LanguageDTO> findAll();

    Optional<LanguageDTO> findById(Integer id);

    LanguageDTO create(LanguageDTO languageDTO);

    LanguageDTO update(LanguageDTO languageDTO);

    void delete(Integer id);
}
