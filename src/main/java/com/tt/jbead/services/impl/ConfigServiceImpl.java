package com.tt.jbead.services.impl;

import com.tt.jbead.domain.dtos.ConfigDTO;
import com.tt.jbead.domain.entities.User;
import com.tt.jbead.repositories.UserRepository;
import com.tt.jbead.services.ConfigService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {


    private UserRepository userRepository;

    public ConfigServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public Optional<ConfigDTO> findByUserId(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        ConfigDTO configDTO = new ConfigDTO();
        return  Optional.of(configDTO.builder().id(user.getId()).theme(user.getTheme()).build());

        //return optionalCity.map(city -> modelMapper.map(city, CityDTO.class));
    }

//    @Override
//    public CityDTO create(CityDTO cityDTO) {
//        City cityToSave = modelMapper.map(cityDTO, City.class);
//        cityToSave.setId(null);
//        City savedCity = cityRepository.save(cityToSave);
//        return modelMapper.map(savedCity, CityDTO.class);
//    }
//
//    @Override
//    public CityDTO update(CityDTO cityDTO) {
//        Integer id = cityDTO.getId();
//        Optional<City> optionalCity = cityRepository.findById(id);
//
//        if(optionalCity.isEmpty()){
//            throw new NotFoundExceptionCity("City not found in database with id: "+id);
//        }
//
//        City cityToUpdate = modelMapper.map(cityDTO, City.class);
//        City savedCity = cityRepository.save(cityToUpdate);
//        return modelMapper.map(savedCity, CityDTO.class);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Optional<City> optionalCity = cityRepository.findById(id);
//
//        if(optionalCity.isPresent()){
//            City cityToDelete = optionalCity.get();
//            cityRepository.delete(cityToDelete);
//        } else {
//            throw new NotFoundExceptionCity("City not found in database with id: "+id);
//        }
//
//    }
}
