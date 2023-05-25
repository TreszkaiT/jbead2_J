package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.SocialMedia;
import com.tt.jbead.domain.dtos.SocialMediaDTO;
import com.tt.jbead.exceptions.NotFoundExceptionSocialMedia;
import com.tt.jbead.repositories.SocialMediaRepository;
import com.tt.jbead.services.SocialMediaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private SocialMediaRepository socialMediaRepository;
    private ModelMapper modelMapper;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.socialMediaRepository = socialMediaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SocialMediaDTO> findAll() {
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();

        return socialMediaList.stream()
                .map(socialMedia -> modelMapper.map(socialMedia, SocialMediaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SocialMediaDTO> findById(Integer id) {
        Optional<SocialMedia> optionalSocialMedia = socialMediaRepository.findById(id);
        return optionalSocialMedia.map(socialMedia -> modelMapper.map(socialMedia, SocialMediaDTO.class));
    }

    @Override
    public SocialMediaDTO create(SocialMediaDTO socialMediaDTO) {
        SocialMedia socialMediaToSave = modelMapper.map(socialMediaDTO, SocialMedia.class);
        socialMediaToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        SocialMedia savedSocialMedia = socialMediaRepository.save(socialMediaToSave);
        return modelMapper.map(savedSocialMedia, SocialMediaDTO.class);
    }

    @Override
    public SocialMediaDTO update(SocialMediaDTO socialMediaDTO) {
        Integer id = socialMediaDTO.getId();
        Optional<SocialMedia> optionalSocialMedia = socialMediaRepository.findById(id);

        if(optionalSocialMedia.isEmpty()){
            throw new NotFoundExceptionSocialMedia("SocialMedia not found in database with id="+id);
        }

        SocialMedia socialMediaToUpdate = modelMapper.map(socialMediaDTO, SocialMedia.class);
        SocialMedia saveSocialMedia = socialMediaRepository.save(socialMediaToUpdate);
        return modelMapper.map(saveSocialMedia, socialMediaDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<SocialMedia> optionalSocialMedia = socialMediaRepository.findById(id);

        if(optionalSocialMedia.isPresent()){
            SocialMedia socialMediaToDelete = optionalSocialMedia.get();
            socialMediaRepository.delete(socialMediaToDelete);
        } else {
            throw new NotFoundExceptionSocialMedia("SocialMedia not found in database with id="+id);
        }
    }
}