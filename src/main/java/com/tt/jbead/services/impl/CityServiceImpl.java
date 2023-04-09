package com.tt.jbead.services.impl;

import com.tt.jbead.domain.dtos.CityDTO;
import com.tt.jbead.domain.entities.City;
import com.tt.jbead.exceptions.NotFoundExceptionCity;
import com.tt.jbead.repositories.CityRepository;
import com.tt.jbead.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private ModelMapper modelMapper;

    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper){
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CityDTO> findAll() {
        List<City> cityList = cityRepository.findAll();

        return cityList.stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CityDTO> findById(Integer id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        return optionalCity.map(city -> modelMapper.map(city, CityDTO.class));
    }

    @Override
    public CityDTO create(CityDTO cityDTO) {
        City cityToSave = modelMapper.map(cityDTO, City.class);
        cityToSave.setId(null);
        City savedCity = cityRepository.save(cityToSave);
        return modelMapper.map(savedCity, CityDTO.class);
    }

    @Override
    public CityDTO update(CityDTO cityDTO) {
        Integer id = cityDTO.getId();
        Optional<City> optionalCity = cityRepository.findById(id);

        if(optionalCity.isEmpty()){
            throw new NotFoundExceptionCity("City not found in database with id: "+id);
        }

        City cityToUpdate = modelMapper.map(cityDTO, City.class);
        City savedCity = cityRepository.save(cityToUpdate);
        return modelMapper.map(savedCity, CityDTO.class);
    }

    @Override
    public void delete(Integer id) {
        Optional<City> optionalCity = cityRepository.findById(id);

        if(optionalCity.isPresent()){
            City cityToDelete = optionalCity.get();
            cityRepository.delete(cityToDelete);
        } else {
            throw new NotFoundExceptionCity("City not found in database with id: "+id);
        }

    }
}
