package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.Study;
import com.tt.jbead.domain.dtos.StudyDTO;
import com.tt.jbead.exceptions.NotFoundExceptionStudy;
import com.tt.jbead.repositories.StudyRepository;
import com.tt.jbead.services.StudyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyServiceImpl implements StudyService {

    private StudyRepository studyRepository;
    private ModelMapper modelMapper;

    public StudyServiceImpl(StudyRepository studyRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.studyRepository = studyRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<StudyDTO> findAll() {
        List<Study> studyList = studyRepository.findAll();

        return studyList.stream()
                .map(study -> modelMapper.map(study, StudyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudyDTO create(StudyDTO studyDTO) {
        Study studyToSave = modelMapper.map(studyDTO, Study.class);
        studyToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        Study savedStudy = studyRepository.save(studyToSave);
        return modelMapper.map(savedStudy, StudyDTO.class);
    }

    @Override
    public Optional<StudyDTO> findById(Integer id) {
        Optional<Study> optionalStudy = studyRepository.findById(id);
        return optionalStudy.map(study -> modelMapper.map(study, StudyDTO.class));
    }

    @Override
    public StudyDTO update(StudyDTO studyDTO) {
        Integer id = studyDTO.getId();
        Optional<Study> optionalStudy = studyRepository.findById(id);

        if(optionalStudy.isEmpty()){
            throw new NotFoundExceptionStudy("Study not found in database with id="+id);
        }

        Study studyToUpdate = modelMapper.map(studyDTO, Study.class);
        Study saveStudy = studyRepository.save(studyToUpdate);
        return modelMapper.map(saveStudy, studyDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<Study> optionalStudy = studyRepository.findById(id);

        if(optionalStudy.isPresent()){
            Study studyToDelete = optionalStudy.get();
            studyRepository.delete(studyToDelete);
        } else {
            throw new NotFoundExceptionStudy("Study not found in database with id="+id);
        }
    }
}