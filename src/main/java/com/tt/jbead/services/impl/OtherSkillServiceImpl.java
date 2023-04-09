package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.OtherSkill;
import com.tt.jbead.domain.dtos.OtherSkillDTO;
import com.tt.jbead.exceptions.NotFoundExceptionOtherSkill;
import com.tt.jbead.repositories.OtherSkillRepository;
import com.tt.jbead.services.OtherSkillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OtherSkillServiceImpl implements OtherSkillService {

    private OtherSkillRepository otherSkillRepository;
    private ModelMapper modelMapper;

    public OtherSkillServiceImpl(OtherSkillRepository otherSkillRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.otherSkillRepository = otherSkillRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<OtherSkillDTO> findAll() {
        List<OtherSkill> otherSkillList = otherSkillRepository.findAll();

        return otherSkillList.stream()
                .map(otherSkill -> modelMapper.map(otherSkill, OtherSkillDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OtherSkillDTO create(OtherSkillDTO otherSkillDTO) {
        OtherSkill otherSkillToSave = modelMapper.map(otherSkillDTO, OtherSkill.class);
        otherSkillToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        OtherSkill savedOtherSkill = otherSkillRepository.save(otherSkillToSave);
        return modelMapper.map(savedOtherSkill, OtherSkillDTO.class);
    }

    @Override
    public Optional<OtherSkillDTO> findById(Integer id) {
        Optional<OtherSkill> optionalOtherSkill = otherSkillRepository.findById(id);
        return optionalOtherSkill.map(otherSkill -> modelMapper.map(otherSkill, OtherSkillDTO.class));
    }

    @Override
    public OtherSkillDTO update(OtherSkillDTO otherSkillDTO) {
        Integer id = otherSkillDTO.getId();
        Optional<OtherSkill> optionalOtherSkill = otherSkillRepository.findById(id);

        if(optionalOtherSkill.isEmpty()){
            throw new NotFoundExceptionOtherSkill("OtherSkill not found in database with id="+id);
        }

        OtherSkill otherSkillToUpdate = modelMapper.map(otherSkillDTO, OtherSkill.class);
        OtherSkill saveOtherSkill = otherSkillRepository.save(otherSkillToUpdate);
        return modelMapper.map(saveOtherSkill, otherSkillDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<OtherSkill> optionalOtherSkill = otherSkillRepository.findById(id);

        if(optionalOtherSkill.isPresent()){
            OtherSkill otherSkillToDelete = optionalOtherSkill.get();
            otherSkillRepository.delete(otherSkillToDelete);
        } else {
            throw new NotFoundExceptionOtherSkill("OtherSkill not found in database with id="+id);
        }
    }
}