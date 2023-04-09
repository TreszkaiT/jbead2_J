package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.CityDTO;
import com.tt.jbead.domain.dtos.OtherSkillDTO;

import java.util.List;
import java.util.Optional;

public interface OtherSkillService {

    List<OtherSkillDTO> findAll();

    Optional<OtherSkillDTO> findById(Integer id);

    OtherSkillDTO create(OtherSkillDTO otherSkillDTO);

    OtherSkillDTO update(OtherSkillDTO otherSkillDTO);

    void delete(Integer id);
}
