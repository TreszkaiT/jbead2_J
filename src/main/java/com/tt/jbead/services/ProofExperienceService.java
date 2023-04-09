package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.ProofExperienceDTO;

import java.util.List;
import java.util.Optional;

public interface ProofExperienceService {

    List<ProofExperienceDTO> findAll();

    Optional<ProofExperienceDTO> findById(Integer id);

    ProofExperienceDTO create(ProofExperienceDTO proofExperienceDTO);

    ProofExperienceDTO update(ProofExperienceDTO proofExperienceDTO);

    void delete(Integer id);
}
