package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.Language;
import com.tt.jbead.domain.dtos.LanguageDTO;
import com.tt.jbead.exceptions.NotFoundExceptionLanguage;
import com.tt.jbead.repositories.LanguageRepository;
import com.tt.jbead.services.LanguageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;
    private ModelMapper modelMapper;

    public LanguageServiceImpl(LanguageRepository languageRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<LanguageDTO> findAll() {
        List<Language> languageList = languageRepository.findAll();

        return languageList.stream()
                .map(language -> modelMapper.map(language, LanguageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LanguageDTO create(LanguageDTO languageDTO) {
        Language languageToSave = modelMapper.map(languageDTO, Language.class);
        languageToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        Language savedLanguage = languageRepository.save(languageToSave);
        return modelMapper.map(savedLanguage, LanguageDTO.class);
    }

    @Override
    public Optional<LanguageDTO> findById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.map(language -> modelMapper.map(language, LanguageDTO.class));
    }

    @Override
    public LanguageDTO update(LanguageDTO languageDTO) {
        Integer id = languageDTO.getId();
        Optional<Language> optionalLanguage = languageRepository.findById(id);

        if(optionalLanguage.isEmpty()){
            throw new NotFoundExceptionLanguage("Language not found in database with id="+id);
        }

        Language languageToUpdate = modelMapper.map(languageDTO, Language.class);
        Language saveLanguage = languageRepository.save(languageToUpdate);
        return modelMapper.map(saveLanguage, languageDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);

        if(optionalLanguage.isPresent()){
            Language languageToDelete = optionalLanguage.get();
            languageRepository.delete(languageToDelete);
        } else {
            throw new NotFoundExceptionLanguage("Language not found in database with id="+id);
        }
    }
}