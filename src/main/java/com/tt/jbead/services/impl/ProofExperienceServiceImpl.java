package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.ProofExperience;
import com.tt.jbead.domain.dtos.ProofExperienceDTO;
import com.tt.jbead.exceptions.NotFoundExceptionProofExperience;
import com.tt.jbead.repositories.ProofExperienceRepository;
import com.tt.jbead.services.ProofExperienceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProofExperienceServiceImpl implements ProofExperienceService {

    private final ProofExperienceRepository proofExperienceRepository;
    private final ModelMapper modelMapper;

    public ProofExperienceServiceImpl(ProofExperienceRepository proofExperienceRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.proofExperienceRepository = proofExperienceRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ProofExperienceDTO> findAll() {
        List<ProofExperience> proofExperienceList = proofExperienceRepository.findAll();

        return proofExperienceList.stream()
                .map(proofExperience -> modelMapper.map(proofExperience, ProofExperienceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProofExperienceDTO> findById(Integer id) {
        Optional<ProofExperience> optionalProofExperience = proofExperienceRepository.findById(id);
        return optionalProofExperience.map(proofExperience -> modelMapper.map(proofExperience, ProofExperienceDTO.class));
    }

    @Override
    public ProofExperienceDTO create(ProofExperienceDTO proofExperienceDTO) {
        ProofExperience proofExperienceToSave = modelMapper.map(proofExperienceDTO, ProofExperience.class);
        proofExperienceToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        ProofExperience savedProofExperience = proofExperienceRepository.save(proofExperienceToSave);
        return modelMapper.map(savedProofExperience, ProofExperienceDTO.class);
    }


    @Override
    public ProofExperienceDTO update(ProofExperienceDTO proofExperienceDTO) {
        Integer id = proofExperienceDTO.getId();
        Optional<ProofExperience> optionalProofExperience = proofExperienceRepository.findById(id);

        if(optionalProofExperience.isEmpty()){
            throw new NotFoundExceptionProofExperience("ProofExperience not found in database with id="+id);
        }

        ProofExperience proofExperienceToUpdate = modelMapper.map(proofExperienceDTO, ProofExperience.class);
        ProofExperience saveProofExperience = proofExperienceRepository.save(proofExperienceToUpdate);
        return modelMapper.map(saveProofExperience, proofExperienceDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<ProofExperience> optionalProofExperience = proofExperienceRepository.findById(id);

        if(optionalProofExperience.isPresent()){
            ProofExperience proofExperienceToDelete = optionalProofExperience.get();
            proofExperienceRepository.delete(proofExperienceToDelete);
        } else {
            throw new NotFoundExceptionProofExperience("ProofExperience not found in database with id="+id);
        }
    }
}