package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.Picture;
import com.tt.jbead.domain.dtos.PictureDTO;
import com.tt.jbead.exceptions.notfound.PictureNotFoundException;
import com.tt.jbead.repositories.PictureRepository;
import com.tt.jbead.services.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PictureDTO> findAll() {
        List<Picture> pictureList = pictureRepository.findAll();

        return pictureList.stream()
                .map(picture -> modelMapper.map(picture, PictureDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PictureDTO> findById(Integer id) {
        Optional<Picture> optionalPicture = pictureRepository.findById(id);
        return optionalPicture.map(picture -> modelMapper.map(picture, PictureDTO.class));
    }

    @Override
    public PictureDTO create(PictureDTO pictureDTO) {
        Picture pictureToSave = modelMapper.map(pictureDTO, Picture.class);
        pictureToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        Picture savedPicture = pictureRepository.save(pictureToSave);
        return modelMapper.map(savedPicture, PictureDTO.class);
    }

    @Override
    public PictureDTO update(PictureDTO pictureDTO) {
        Integer id = pictureDTO.getId();
        Optional<Picture> optionalPicture = pictureRepository.findById(id);

        if(optionalPicture.isEmpty()){
            throw new PictureNotFoundException("Picture not found in database with id="+id);
        }

        Picture pictureToUpdate = modelMapper.map(pictureDTO, Picture.class);
        Picture savePicture = pictureRepository.save(pictureToUpdate);
        return modelMapper.map(savePicture, pictureDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<Picture> optionalPicture = pictureRepository.findById(id);

        if(optionalPicture.isPresent()){
            Picture pictureToDelete = optionalPicture.get();
            pictureRepository.delete(pictureToDelete);
        } else {
            throw new PictureNotFoundException("Picture not found in database with id="+id);
        }
    }
}