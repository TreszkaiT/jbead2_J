package com.tt.jbead.services;

import com.tt.jbead.domain.dtos.PictureDTO;

import java.util.List;
import java.util.Optional;

public interface PictureService {

    List<PictureDTO> findAll();

    Optional<PictureDTO> findById(Integer id);

    PictureDTO create(PictureDTO pictureDTO);

    PictureDTO update(PictureDTO pictureDTO);

    void delete(Integer id);
}
