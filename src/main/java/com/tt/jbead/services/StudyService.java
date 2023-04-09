package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.StudyDTO;

import java.util.List;
import java.util.Optional;

public interface StudyService {

    List<StudyDTO> findAll();

    Optional<StudyDTO> findById(Integer id);

    StudyDTO create(StudyDTO studyDTO);

    StudyDTO update(StudyDTO studyDTO);

    void delete(Integer id);
}
