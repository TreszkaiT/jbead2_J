package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.SocialMediaDTO;

import java.util.List;
import java.util.Optional;

public interface SocialMediaService {

    List<SocialMediaDTO> findAll();

    Optional<SocialMediaDTO> findById(Integer id);

    SocialMediaDTO create(SocialMediaDTO socialMediaDTO);

    SocialMediaDTO update(SocialMediaDTO socialMediaDTO);

    void delete(Integer id);
}
