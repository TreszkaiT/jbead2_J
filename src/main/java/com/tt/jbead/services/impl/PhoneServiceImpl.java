package com.tt.jbead.services.impl;

import com.tt.jbead.domain.entities.Phone;
import com.tt.jbead.domain.dtos.PhoneDTO;
import com.tt.jbead.exceptions.NotFoundExceptionPhone;
import com.tt.jbead.repositories.PhoneRepository;
import com.tt.jbead.services.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final ModelMapper modelMapper;

    public PhoneServiceImpl(PhoneRepository phoneRepository, ModelMapper modelMapper) {        // Spring Data Injection - with in Constructor
        this.phoneRepository = phoneRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PhoneDTO> findAll() {
        List<Phone> phoneList = phoneRepository.findAll();

        return phoneList.stream()
                .map(phone -> modelMapper.map(phone, PhoneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PhoneDTO> findById(Integer id) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        return optionalPhone.map(phone -> modelMapper.map(phone, PhoneDTO.class));
    }

    @Override
    public PhoneDTO create(PhoneDTO phoneDTO) {
        Phone phoneToSave = modelMapper.map(phoneDTO, Phone.class);
        phoneToSave.setId(null);                                                // védelem, hogy ne írjuk felül a már meglévő is-jű elemeket a DB-ban. Null-nál ugyanis új id-val generálja az új rekordokat a Spring
        Phone savedPhone = phoneRepository.save(phoneToSave);
        return modelMapper.map(savedPhone, PhoneDTO.class);
    }

    @Override
    public PhoneDTO update(PhoneDTO phoneDTO) {
        Integer id = phoneDTO.getId();
        Optional<Phone> optionalPhone = phoneRepository.findById(id);

        if(optionalPhone.isEmpty()){
            throw new NotFoundExceptionPhone("Phone not found in database with id="+id);
        }

        Phone phoneToUpdate = modelMapper.map(phoneDTO, Phone.class);
        Phone savePhone = phoneRepository.save(phoneToUpdate);
        return modelMapper.map(savePhone, phoneDTO.getClass());
    }

    @Override
    public void delete(Integer id) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);

        if(optionalPhone.isPresent()){
            Phone phoneToDelete = optionalPhone.get();
            phoneRepository.delete(phoneToDelete);
        } else {
            throw new NotFoundExceptionPhone("Phone not found in database with id="+id);
        }
    }
}