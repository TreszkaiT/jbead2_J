package com.tt.jbead.services.impl;

import com.tt.jbead.domain.dtos.ConfigDTO;
import com.tt.jbead.domain.entities.User;
import com.tt.jbead.repositories.UserRepository;
import com.tt.jbead.services.ConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        //User user = optionalUser.get();
        User user = new User();

        ConfigDTO configDTO = new ConfigDTO();
        if(optionalUser.isPresent()){
            user = optionalUser.get();

            return  Optional.of(configDTO.builder().id(user.getId()).theme(user.getTheme()).build());
        } else {
            //error;
            return  Optional.of(configDTO);
        }
        //return optionalCity.map(city -> modelMapper.map(city, CityDTO.class));
    }

    @Override
    public Optional<ConfigDTO> updateByUserId(ConfigDTO configDTO){
        Optional<User> optionalUser = userRepository.findById(configDTO.getId());
        //User user = optionalUser.get();
        User user = new User();

        if(optionalUser.isPresent()){
            user = optionalUser.get();
            user.setTheme(configDTO.getTheme());
            userRepository.save(user);
        } else {
            //error;
        }
        return Optional.of(configDTO);
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
